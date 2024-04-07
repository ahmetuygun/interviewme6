import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { PersonalDetailService } from 'app/entities/personal-detail/service/personal-detail.service';
import { IMembershipLevel } from 'app/entities/membership-level/membership-level.model';
import { MembershipLevelService } from 'app/entities/membership-level/service/membership-level.service';
import { IntervieweeService } from '../service/interviewee.service';
import { IInterviewee } from '../interviewee.model';
import { IntervieweeFormService, IntervieweeFormGroup } from './interviewee-form.service';

@Component({
  standalone: true,
  selector: 'jhi-interviewee-update',
  templateUrl: './interviewee-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IntervieweeUpdateComponent implements OnInit {
  isSaving = false;
  interviewee: IInterviewee | null = null;

  usersSharedCollection: IUser[] = [];
  personalDetailsCollection: IPersonalDetail[] = [];
  membershipLevelsSharedCollection: IMembershipLevel[] = [];

  editForm: IntervieweeFormGroup = this.intervieweeFormService.createIntervieweeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected intervieweeService: IntervieweeService,
    protected intervieweeFormService: IntervieweeFormService,
    protected userService: UserService,
    protected personalDetailService: PersonalDetailService,
    protected membershipLevelService: MembershipLevelService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private router: Router,
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  comparePersonalDetail = (o1: IPersonalDetail | null, o2: IPersonalDetail | null): boolean =>
    this.personalDetailService.comparePersonalDetail(o1, o2);

  compareMembershipLevel = (o1: IMembershipLevel | null, o2: IMembershipLevel | null): boolean =>
    this.membershipLevelService.compareMembershipLevel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewee }) => {
      this.interviewee = interviewee;
      if (interviewee) {
        this.updateForm(interviewee);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('interviewme6App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewee = this.intervieweeFormService.getInterviewee(this.editForm);
    if (interviewee.id !== null) {
      this.subscribeToSaveResponse(this.intervieweeService.update(interviewee));
    } else {
      this.subscribeToSaveResponse(this.intervieweeService.create(interviewee));
    }


  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewee>>): void {


    result.pipe(
      finalize(() => this.onSaveFinalize())
    ).subscribe(  (response: HttpResponse<IInterviewee>) => {
      this.onSaveSuccess(response);
      this.onSaveError();
    });
  }

  protected onSaveSuccess(response: HttpResponse<IInterviewee>): void {
    const interviewerId = response.body?.id;
    if (interviewerId) {
      this.router.navigate(['/interviewee', interviewerId, 'view']);
    } else {
      // Handle the case where interviewerId is not available
    }
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(interviewee: IInterviewee): void {
    this.interviewee = interviewee;
    this.intervieweeFormService.resetForm(this.editForm, interviewee);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, interviewee.internalUser);
    this.personalDetailsCollection = this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
      this.personalDetailsCollection,
      interviewee.personalDetail,
    );
    this.membershipLevelsSharedCollection = this.membershipLevelService.addMembershipLevelToCollectionIfMissing<IMembershipLevel>(
      this.membershipLevelsSharedCollection,
      interviewee.membership,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.interviewee?.internalUser)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.personalDetailService
      .query({ 'intervieweeId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPersonalDetail[]>) => res.body ?? []))
      .pipe(
        map((personalDetails: IPersonalDetail[]) =>
          this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
            personalDetails,
            this.interviewee?.personalDetail,
          ),
        ),
      )
      .subscribe((personalDetails: IPersonalDetail[]) => (this.personalDetailsCollection = personalDetails));

    this.membershipLevelService
      .query()
      .pipe(map((res: HttpResponse<IMembershipLevel[]>) => res.body ?? []))
      .pipe(
        map((membershipLevels: IMembershipLevel[]) =>
          this.membershipLevelService.addMembershipLevelToCollectionIfMissing<IMembershipLevel>(
            membershipLevels,
            this.interviewee?.membership,
          ),
        ),
      )
      .subscribe((membershipLevels: IMembershipLevel[]) => (this.membershipLevelsSharedCollection = membershipLevels));
  }
}
