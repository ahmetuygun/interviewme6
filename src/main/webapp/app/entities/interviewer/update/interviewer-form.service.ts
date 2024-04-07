import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInterviewer, NewInterviewer } from '../interviewer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterviewer for edit and NewInterviewerFormGroupInput for create.
 */
type InterviewerFormGroupInput = IInterviewer | PartialWithRequiredKeyOf<NewInterviewer>;

type InterviewerFormDefaults = Pick<NewInterviewer, 'id'>;

type InterviewerFormGroupContent = {
  id: FormControl<IInterviewer['id'] | NewInterviewer['id']>;
  photo: FormControl<IInterviewer['photo']>;
  photoContentType: FormControl<IInterviewer['photoContentType']>;
  internalUser: FormControl<IInterviewer['internalUser']>;
  personalDetail: FormControl<IInterviewer['personalDetail']>;
  email: FormControl<IInterviewer['email']>;
  cv: FormControl<IInterviewer['cv']>;
  cvContentType: FormControl<IInterviewer['cvContentType']>;
};

export type InterviewerFormGroup = FormGroup<InterviewerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterviewerFormService {
  createInterviewerFormGroup(interviewer: InterviewerFormGroupInput = { id: null }): InterviewerFormGroup {
    const interviewerRawValue = {
      ...this.getFormDefaults(),
      ...interviewer,
    };
    return new FormGroup<InterviewerFormGroupContent>({
      id: new FormControl(
        { value: interviewerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      photo: new FormControl(interviewerRawValue.photo),
      photoContentType: new FormControl(interviewerRawValue.photoContentType),
      internalUser: new FormControl(interviewerRawValue.internalUser),
      personalDetail: new FormControl(interviewerRawValue.personalDetail),
      email: new FormControl(interviewerRawValue.email),
      cv: new FormControl(interviewerRawValue.cv),
      cvContentType: new FormControl(interviewerRawValue.cvContentType),
    });
  }

  getInterviewer(form: InterviewerFormGroup): IInterviewer | NewInterviewer {
    return form.getRawValue() as IInterviewer | NewInterviewer;
  }

  resetForm(form: InterviewerFormGroup, interviewer: InterviewerFormGroupInput): void {
    const interviewerRawValue = { ...this.getFormDefaults(), ...interviewer };
    form.reset(
      {
        ...interviewerRawValue,
        id: { value: interviewerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InterviewerFormDefaults {
    return {
      id: null,
    };
  }
}
