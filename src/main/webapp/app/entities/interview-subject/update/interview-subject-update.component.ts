import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { InterviewerService } from 'app/entities/interviewer/service/interviewer.service';
import { IInterviewSubject } from '../interview-subject.model';
import { InterviewSubjectService } from '../service/interview-subject.service';
import { InterviewSubjectFormService, InterviewSubjectFormGroup } from './interview-subject-form.service';

@Component({
  standalone: true,
  selector: 'jhi-interview-subject-update',
  templateUrl: './interview-subject-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InterviewSubjectUpdateComponent implements OnInit {
  isSaving = false;
  interviewSubject: IInterviewSubject | null = null;

  interviewersSharedCollection: IInterviewer[] = [];

  editForm: InterviewSubjectFormGroup = this.interviewSubjectFormService.createInterviewSubjectFormGroup();

  constructor(
    protected interviewSubjectService: InterviewSubjectService,
    protected interviewSubjectFormService: InterviewSubjectFormService,
    protected interviewerService: InterviewerService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareInterviewer = (o1: IInterviewer | null, o2: IInterviewer | null): boolean => this.interviewerService.compareInterviewer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewSubject }) => {
      this.interviewSubject = interviewSubject;
      if (interviewSubject) {
        this.updateForm(interviewSubject);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewSubject = this.interviewSubjectFormService.getInterviewSubject(this.editForm);
    if (interviewSubject.id !== null) {
      this.subscribeToSaveResponse(this.interviewSubjectService.update(interviewSubject));
    } else {
      this.subscribeToSaveResponse(this.interviewSubjectService.create(interviewSubject));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewSubject>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(interviewSubject: IInterviewSubject): void {
    this.interviewSubject = interviewSubject;
    this.interviewSubjectFormService.resetForm(this.editForm, interviewSubject);

    this.interviewersSharedCollection = this.interviewerService.addInterviewerToCollectionIfMissing<IInterviewer>(
      this.interviewersSharedCollection,
      interviewSubject.interviewer,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.interviewerService
      .query()
      .pipe(map((res: HttpResponse<IInterviewer[]>) => res.body ?? []))
      .pipe(
        map((interviewers: IInterviewer[]) =>
          this.interviewerService.addInterviewerToCollectionIfMissing<IInterviewer>(interviewers, this.interviewSubject?.interviewer),
        ),
      )
      .subscribe((interviewers: IInterviewer[]) => (this.interviewersSharedCollection = interviewers));
  }
}
