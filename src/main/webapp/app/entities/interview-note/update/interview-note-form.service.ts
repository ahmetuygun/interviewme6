import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInterviewNote, NewInterviewNote } from '../interview-note.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterviewNote for edit and NewInterviewNoteFormGroupInput for create.
 */
type InterviewNoteFormGroupInput = IInterviewNote | PartialWithRequiredKeyOf<NewInterviewNote>;

type InterviewNoteFormDefaults = Pick<NewInterviewNote, 'id'>;

type InterviewNoteFormGroupContent = {
  id: FormControl<IInterviewNote['id'] | NewInterviewNote['id']>;
  noteText: FormControl<IInterviewNote['noteText']>;
  rating: FormControl<IInterviewNote['rating']>;
  actionItems: FormControl<IInterviewNote['actionItems']>;
  feedback: FormControl<IInterviewNote['feedback']>;
};

export type InterviewNoteFormGroup = FormGroup<InterviewNoteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterviewNoteFormService {
  createInterviewNoteFormGroup(interviewNote: InterviewNoteFormGroupInput = { id: null }): InterviewNoteFormGroup {
    const interviewNoteRawValue = {
      ...this.getFormDefaults(),
      ...interviewNote,
    };
    return new FormGroup<InterviewNoteFormGroupContent>({
      id: new FormControl(
        { value: interviewNoteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      noteText: new FormControl(interviewNoteRawValue.noteText),
      rating: new FormControl(interviewNoteRawValue.rating),
      actionItems: new FormControl(interviewNoteRawValue.actionItems),
      feedback: new FormControl(interviewNoteRawValue.feedback),
    });
  }

  getInterviewNote(form: InterviewNoteFormGroup): IInterviewNote | NewInterviewNote {
    return form.getRawValue() as IInterviewNote | NewInterviewNote;
  }

  resetForm(form: InterviewNoteFormGroup, interviewNote: InterviewNoteFormGroupInput): void {
    const interviewNoteRawValue = { ...this.getFormDefaults(), ...interviewNote };
    form.reset(
      {
        ...interviewNoteRawValue,
        id: { value: interviewNoteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InterviewNoteFormDefaults {
    return {
      id: null,
    };
  }
}
