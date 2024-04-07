import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInterviewee, NewInterviewee } from '../interviewee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterviewee for edit and NewIntervieweeFormGroupInput for create.
 */
type IntervieweeFormGroupInput = IInterviewee | PartialWithRequiredKeyOf<NewInterviewee>;

type IntervieweeFormDefaults = Pick<NewInterviewee, 'id'>;

type IntervieweeFormGroupContent = {
  id: FormControl<IInterviewee['id'] | NewInterviewee['id']>;
  photo: FormControl<IInterviewee['photo']>;
  photoContentType: FormControl<IInterviewee['photoContentType']>;
  internalUser: FormControl<IInterviewee['internalUser']>;
  personalDetail: FormControl<IInterviewee['personalDetail']>;
  membership: FormControl<IInterviewee['membership']>;
  cv: FormControl<IInterviewee['cv']>;
  cvContentType: FormControl<IInterviewee['cvContentType']>;


};

export type IntervieweeFormGroup = FormGroup<IntervieweeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IntervieweeFormService {
  createIntervieweeFormGroup(interviewee: IntervieweeFormGroupInput = { id: null }): IntervieweeFormGroup {
    const intervieweeRawValue = {
      ...this.getFormDefaults(),
      ...interviewee,
    };
    return new FormGroup<IntervieweeFormGroupContent>({
      id: new FormControl(
        { value: intervieweeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      photo: new FormControl(intervieweeRawValue.photo),
      photoContentType: new FormControl(intervieweeRawValue.photoContentType),
      internalUser: new FormControl(intervieweeRawValue.internalUser),
      personalDetail: new FormControl(intervieweeRawValue.personalDetail),
      membership: new FormControl(intervieweeRawValue.membership),
      cv: new FormControl(intervieweeRawValue.cv),
      cvContentType: new FormControl(intervieweeRawValue.cvContentType),
    });
  }

  getInterviewee(form: IntervieweeFormGroup): IInterviewee | NewInterviewee {
    return form.getRawValue() as IInterviewee | NewInterviewee;
  }

  resetForm(form: IntervieweeFormGroup, interviewee: IntervieweeFormGroupInput): void {
    const intervieweeRawValue = { ...this.getFormDefaults(), ...interviewee };
    form.reset(
      {
        ...intervieweeRawValue,
        id: { value: intervieweeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IntervieweeFormDefaults {
    return {
      id: null,
    };
  }
}
