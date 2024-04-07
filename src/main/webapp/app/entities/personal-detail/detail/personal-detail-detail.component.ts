import {Component, inject, Input} from '@angular/core';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { DataUtils } from 'app/core/util/data-util.service';
import { IPersonalDetail } from '../personal-detail.model';
import {EducationEntryComponent} from "../../education-entry/list/education-entry.component";
import {WorkExperienceEntryComponent} from "../../work-experience-entry/list/work-experience-entry.component";
import {PersonalDetailService} from "../service/personal-detail.service";
import {catchError, map, mergeMap} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";
import {EMPTY, Observable, of} from "rxjs";
import {ITEM_DELETED_EVENT} from "../../../config/navigation.constants";
import {NgForOf} from "@angular/common";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {
  faHome,
  faPlus,
  faEdit,
  faTrash,
  faMapPin,
  faEnvelope,
  faPhone,
  faLink, faFile
} from '@fortawesome/free-solid-svg-icons';



@Component({
  standalone: true,
  selector: 'jhi-personal-detail-detail',
  templateUrl: './personal-detail-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, EducationEntryComponent, WorkExperienceEntryComponent, NgForOf, FaIconComponent],
})
export class PersonalDetailDetailComponent {
  @Input() personalDetail: IPersonalDetail | null = null;
  @Input() personalDetailId: number | undefined;
// This is the property that will receive the string


  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    private personalDetailService: PersonalDetailService
  ) {}

  ngOnInit(): void {

    if(this.personalDetail == null){
      if (this.personalDetailId) {

        this.personalDetailService.find(this.personalDetailId)
          .pipe(
            map((personalDetail: HttpResponse<IPersonalDetail>) => {
              if (personalDetail.body) {
                this.personalDetail = personalDetail.body;
              } else {
                throw new Error('Personal detail not found');
              }
            }),
            catchError(error => {
              console.error('Error fetching personal detail:', error);
              return EMPTY;
            })
          )
          .subscribe();
      }
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  splitSkills(skillString: string | null | undefined): string[] {
    // Check if the input string is not empty or null
    if (skillString && skillString.trim() !== '') {
      // Split the input string by commas and trim any whitespace
      return skillString.split('-').map(skill => skill.trim());
    } else {
      // Return an empty array if the input string is empty or null
      return [];
    }
  }

  protected readonly faTrash = faTrash;
  protected readonly faEdit = faEdit;
  protected readonly faPlus = faPlus;
  protected readonly faHome = faHome;
  protected readonly faMapPin = faMapPin;
  protected readonly faEnvelope = faEnvelope;
  protected readonly faPhone = faPhone;
  @Input() photoContentType!: string | null | undefined;
  @Input() photo!: string | null | undefined;
  @Input() cv!: string | null | undefined;
  @Input() cvContentType!: string | null | undefined;
  protected readonly faLink = faLink;
  protected readonly faFile = faFile;
}
