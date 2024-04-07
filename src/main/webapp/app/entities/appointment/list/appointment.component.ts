import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { SortService } from 'app/shared/sort/sort.service';
import { IAppointment } from '../appointment.model';
import { EntityArrayResponseType, AppointmentService } from '../service/appointment.service';
import { AppointmentDeleteDialogComponent } from '../delete/appointment-delete-dialog.component';
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {AccountService} from "../../../core/auth/account.service";
import {InterviewerService} from "../../interviewer/service/interviewer.service";
import {IntervieweeService} from "../../interviewee/service/interviewee.service";

@Component({
  standalone: true,
  selector: 'jhi-appointment',
  templateUrl: './appointment.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    FaIconComponent,
  ],
})
export class AppointmentComponent implements OnInit {
  appointments?: IAppointment[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  interviewerId: number | undefined;
  intervieweeId: number | undefined;


  constructor(
    protected appointmentService: AppointmentService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    private route: ActivatedRoute,
    protected sortService: SortService,
    protected modalService: NgbModal,
    private accountService: AccountService,
    private interviewerService: InterviewerService,
    private intervieweeService: IntervieweeService,
  ) {}

  trackId = (_index: number, item: IAppointment): number => this.appointmentService.getAppointmentIdentifier(item);

  ngOnInit(): void {
    this.loadAppointments()
  }

   loadAppointments(): void {
     this.accountService.identity().subscribe(account => {
      if (account?.id) {
        if (account.type === 'INTERVIEWER') {
          this.handleInterviewerLogin(account.id);
        } else if (account.type === 'INTERVIEWEE') {
          this.handleIntervieweeLogin(account.id);
        }

      }
    });

  }

  private handleInterviewerLogin(userId: number): void {
    this.interviewerService.findByInternalUserId(userId).subscribe(
      interviewer => {
        if (interviewer.body && interviewer.body.length > 0) {
          this.interviewerId = interviewer.body[0].id;
          this.load();
      }}
    );
  }

  private handleIntervieweeLogin(userId: number): void {
    this.intervieweeService.findByInternalUserId(userId).subscribe(
      interviewee => {
        if (interviewee.body && interviewee.body.length > 0) {
          this.intervieweeId = interviewee.body[0].id;
          this.load();

        }}
    );
  }



  delete(appointment: IAppointment): void {
    const modalRef = this.modalService.open(AppointmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appointment = appointment;
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
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending)),
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.appointments = this.refineData(dataFromBody);
  }

  getOneHourLater(inputTime: string | null | undefined): string {
    if (inputTime === null || inputTime === undefined) {
      return ''; // Return an empty string or handle null case as appropriate
    }
    // Split the input time string to hours and minutes
    const [hours, minutes] = inputTime.split(':').map(Number);

    // Calculate one hour later
    let newHours = hours + 1;
    let newMinutes = minutes;
    if (newHours >= 24) {
      newHours -= 24;
    }

    // Format the result
    const newTimeString = `${this.padZero(newHours)}:${this.padZero(newMinutes)}`;
    return newTimeString;
  }
  private padZero(num: number): string {
    return num < 10 ? `0${num}` : `${num}`;
  }

  formatDate(appointment?: IAppointment | undefined): string {
    if (!appointment || appointment.year === undefined || appointment.month === undefined || appointment.day === undefined) {
      return 'Invalid date';
    }

    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    if(appointment?.month){
      const month = months[appointment.month - 1]; // Adjust month index to match array index
      return `${month} ${appointment.day}, ${appointment.year}`;
    }
    return '';
  }

  protected refineData(data: IAppointment[]): IAppointment[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IAppointment[] | null): IAppointment[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    if (this.intervieweeId !== undefined) {
      const personalDetailIds: string[] = [this.intervieweeId.toString()];
      queryObject['intervieweeId.in'] = personalDetailIds;
    }
    if (this.interviewerId !== undefined) {
      const personalDetailIds: string[] = [this.interviewerId.toString()];
      queryObject['interviewerId.in'] = personalDetailIds;
    }
    return this.appointmentService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

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

  goToInterviewRoom(appointment: IAppointment) {
    let userId: string = ''; // Change const to let and specify the type as string
    if (this.intervieweeId !== undefined) {
      userId = this.intervieweeId.toString();
    }
    if (this.interviewerId !== undefined) {
      userId = this.interviewerId.toString();
    }
    this.router.navigate(['/call', userId, appointment?.appointmentUid]);
  }

}
