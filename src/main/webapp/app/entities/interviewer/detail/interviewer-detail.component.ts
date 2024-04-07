import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { DataUtils } from 'app/core/util/data-util.service';
import { IInterviewer } from '../interviewer.model';
import {PersonalDetailDetailComponent} from "../../personal-detail/detail/personal-detail-detail.component";
import {WorkExperienceEntryComponent} from "../../work-experience-entry/list/work-experience-entry.component";
import {SlotUpdateComponent} from "../../slot/update/slot-update.component";

import {AccountService} from "../../../core/auth/account.service";
import {InterviewerService} from "../service/interviewer.service";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {NgIf} from "@angular/common";
import {AppointmentUpdateComponent} from "../../appointment/update/appointment-update.component";

@Component({
  standalone: true,
  selector: 'jhi-interviewer-detail',
  templateUrl: './interviewer-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, PersonalDetailDetailComponent, WorkExperienceEntryComponent, SlotUpdateComponent, FaIconComponent, NgIf, AppointmentUpdateComponent],
})
export class InterviewerDetailComponent {
  @Input() interviewer: IInterviewer | null = null;

  isOwnProfile : null | false | boolean = false;

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
  private interviewerService: InterviewerService,
  ) {


  }

  ngOnInit(): void {
    this.viewingMyProfile()
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

  public viewingMyProfile(): void  {
    this.accountService.identity().subscribe(account => {
      if (account?.id) {
        this.interviewerService.findByInternalUserId(account.id).subscribe(interviewer => {
          this.isOwnProfile =  interviewer.body && interviewer.body.length > 0 && interviewer.body[0].id == this.interviewer?.id ;
        });
      }
    });
  }
}
