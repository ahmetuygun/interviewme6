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
import { InterviewerService } from '../service/interviewer.service';
import { IInterviewer } from '../interviewer.model';
import { InterviewerFormService, InterviewerFormGroup } from './interviewer-form.service';
import {PersonalDetailUpdateComponent} from "../../personal-detail/update/personal-detail-update.component";

@Component({
  standalone: true,
  selector: 'jhi-interviewer-update',
  templateUrl: './interviewer-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, PersonalDetailUpdateComponent],
})
export class InterviewerUpdateComponent implements OnInit {
  isSaving = false;
  interviewer: IInterviewer | null = null;

  usersSharedCollection: IUser[] = [];
  personalDetailsCollection: IPersonalDetail[] = [];

  editForm: InterviewerFormGroup = this.interviewerFormService.createInterviewerFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected interviewerService: InterviewerService,
    protected interviewerFormService: InterviewerFormService,
    protected userService: UserService,
    protected personalDetailService: PersonalDetailService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private router: Router,
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  comparePersonalDetail = (o1: IPersonalDetail | null, o2: IPersonalDetail | null): boolean =>
    this.personalDetailService.comparePersonalDetail(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewer }) => {
      this.interviewer = interviewer;
      if (interviewer) {
        this.updateForm(interviewer);
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
    const interviewer = this.interviewerFormService.getInterviewer(this.editForm);
    if (interviewer.id !== null) {
      this.subscribeToSaveResponse(this.interviewerService.update(interviewer));
    } else {
      this.subscribeToSaveResponse(this.interviewerService.create(interviewer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewer>>): void {

    result.pipe(
      finalize(() => this.onSaveFinalize())
    ).subscribe(  (response: HttpResponse<IInterviewer>) => {
      this.onSaveSuccess(response);
      this.onSaveError();
    });


  }

  protected onSaveSuccess(response: HttpResponse<IInterviewer>): void {
      const interviewerId = response.body?.id;
      if (interviewerId) {
        this.router.navigate(['/interviewer', interviewerId, 'view']);
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

  protected updateForm(interviewer: IInterviewer): void {
    this.interviewer = interviewer;
    this.interviewerFormService.resetForm(this.editForm, interviewer);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, interviewer.internalUser);
    this.personalDetailsCollection = this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
      this.personalDetailsCollection,
      interviewer.personalDetail,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.interviewer?.internalUser)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.personalDetailService
      .query({ 'interviewerId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPersonalDetail[]>) => res.body ?? []))
      .pipe(
        map((personalDetails: IPersonalDetail[]) =>
          this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
            personalDetails,
            this.interviewer?.personalDetail,
          ),
        ),
      )
      .subscribe((personalDetails: IPersonalDetail[]) => (this.personalDetailsCollection = personalDetails));
  }
}
