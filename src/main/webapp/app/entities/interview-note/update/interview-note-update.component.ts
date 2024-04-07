import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IInterviewNote } from '../interview-note.model';
import { InterviewNoteService } from '../service/interview-note.service';
import { InterviewNoteFormService, InterviewNoteFormGroup } from './interview-note-form.service';

@Component({
  standalone: true,
  selector: 'jhi-interview-note-update',
  templateUrl: './interview-note-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InterviewNoteUpdateComponent implements OnInit {
  isSaving = false;
  interviewNote: IInterviewNote | null = null;

  editForm: InterviewNoteFormGroup = this.interviewNoteFormService.createInterviewNoteFormGroup();

  constructor(
    protected interviewNoteService: InterviewNoteService,
    protected interviewNoteFormService: InterviewNoteFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewNote }) => {
      this.interviewNote = interviewNote;
      if (interviewNote) {
        this.updateForm(interviewNote);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewNote = this.interviewNoteFormService.getInterviewNote(this.editForm);
    if (interviewNote.id !== null) {
      this.subscribeToSaveResponse(this.interviewNoteService.update(interviewNote));
    } else {
      this.subscribeToSaveResponse(this.interviewNoteService.create(interviewNote));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewNote>>): void {
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

  protected updateForm(interviewNote: IInterviewNote): void {
    this.interviewNote = interviewNote;
    this.interviewNoteFormService.resetForm(this.editForm, interviewNote);
  }
}
