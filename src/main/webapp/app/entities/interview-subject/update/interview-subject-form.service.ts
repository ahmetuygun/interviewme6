import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInterviewSubject, NewInterviewSubject } from '../interview-subject.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterviewSubject for edit and NewInterviewSubjectFormGroupInput for create.
 */
type InterviewSubjectFormGroupInput = IInterviewSubject | PartialWithRequiredKeyOf<NewInterviewSubject>;

type InterviewSubjectFormDefaults = Pick<NewInterviewSubject, 'id'>;

type InterviewSubjectFormGroupContent = {
  id: FormControl<IInterviewSubject['id'] | NewInterviewSubject['id']>;
  name: FormControl<IInterviewSubject['name']>;
  parent: FormControl<IInterviewSubject['parent']>;
  interviewer: FormControl<IInterviewSubject['interviewer']>;
};

export type InterviewSubjectFormGroup = FormGroup<InterviewSubjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterviewSubjectFormService {
  createInterviewSubjectFormGroup(interviewSubject: InterviewSubjectFormGroupInput = { id: null }): InterviewSubjectFormGroup {
    const interviewSubjectRawValue = {
      ...this.getFormDefaults(),
      ...interviewSubject,
    };
    return new FormGroup<InterviewSubjectFormGroupContent>({
      id: new FormControl(
        { value: interviewSubjectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(interviewSubjectRawValue.name),
      parent: new FormControl(interviewSubjectRawValue.parent),
      interviewer: new FormControl(interviewSubjectRawValue.interviewer),
    });
  }

  getInterviewSubject(form: InterviewSubjectFormGroup): IInterviewSubject | NewInterviewSubject {
    return form.getRawValue() as IInterviewSubject | NewInterviewSubject;
  }

  resetForm(form: InterviewSubjectFormGroup, interviewSubject: InterviewSubjectFormGroupInput): void {
    const interviewSubjectRawValue = { ...this.getFormDefaults(), ...interviewSubject };
    form.reset(
      {
        ...interviewSubjectRawValue,
        id: { value: interviewSubjectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InterviewSubjectFormDefaults {
    return {
      id: null,
    };
  }
}
