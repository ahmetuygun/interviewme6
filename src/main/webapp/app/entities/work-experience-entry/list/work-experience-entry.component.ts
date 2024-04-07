import {Component, Input, OnInit} from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import {NgbModal, NgbPagination} from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { FilterComponent, FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter';
import { IWorkExperienceEntry } from '../work-experience-entry.model';

import { EntityArrayResponseType, WorkExperienceEntryService } from '../service/work-experience-entry.service';
import { WorkExperienceEntryDeleteDialogComponent } from '../delete/work-experience-entry-delete-dialog.component';
import ItemCountComponent from "../../../shared/pagination/item-count.component";
import {NgForOf, NgIf} from "@angular/common";
import dayjs from "dayjs/esm";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  standalone: true,
  selector: 'jhi-work-experience-entry',
  templateUrl: './work-experience-entry.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    FilterComponent,
    ItemCountComponent,
    NgbPagination,
    ItemCountComponent,
    ItemCountComponent,
    NgForOf,
    NgIf,
    FaIconComponent,
  ],
})
export class WorkExperienceEntryComponent implements OnInit {

  @Input() personalDetailId: number | undefined;

  workExperienceEntries?: IWorkExperienceEntry[];
  isLoading = false;

  predicate = 'id';
  ascending = true;
  filters: IFilterOptions = new FilterOptions();

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  constructor(
    protected workExperienceEntryService: WorkExperienceEntryService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
  ) {}

  trackId = (_index: number, item: IWorkExperienceEntry): number => this.workExperienceEntryService.getWorkExperienceEntryIdentifier(item);

  ngOnInit(): void {

    if (this.personalDetailId !== undefined) {
      const personalDetailIds: string[] = [this.personalDetailId.toString()];
      this.filters.addFilter('personalDetailId.in',...personalDetailIds);
    }


    this.load();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
  }

  delete(workExperienceEntry: IWorkExperienceEntry): void {
    const modalRef = this.modalService.open(WorkExperienceEntryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workExperienceEntry = workExperienceEntry;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations()),
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions)),
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) || 'id,asc').split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    this.filters.initializeFromParams(params);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.workExperienceEntries = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IWorkExperienceEntry[] | null): IWorkExperienceEntry[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(
    page?: number,
    predicate?: string,
    ascending?: boolean,
    filterOptions?: IFilterOption[],
  ): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    filterOptions?.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    if (this.personalDetailId !== undefined) {
      const personalDetailIds: string[] = [this.personalDetailId.toString()];
      queryObject['personalDetailId.in'] = personalDetailIds;
    }
    return this.workExperienceEntryService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }


  getPeriod(startDate: dayjs.Dayjs | null | undefined, endDate: dayjs.Dayjs | null | undefined) {
    if (!startDate || !endDate) {
      return ""; // Return empty string if either start date or end date is missing
    }

    // Extract years from start and end dates
    const startYear = startDate.year();
    const endYear = endDate.year();

    // Return the range of years separated by commas
    return `${startYear}-${endYear}`;
  }

  toCapital(str: string | null | undefined) {
    return str?.toLowerCase().replace(/(?:^|\s)\w/g, function(match) {
      return match.toUpperCase();
    });
  }

}
