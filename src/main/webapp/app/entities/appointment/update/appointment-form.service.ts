import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAppointment, NewAppointment } from '../appointment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppointment for edit and NewAppointmentFormGroupInput for create.
 */
type AppointmentFormGroupInput = IAppointment | PartialWithRequiredKeyOf<NewAppointment>;

type AppointmentFormDefaults = Pick<NewAppointment, 'id'>;

type AppointmentFormGroupContent = {
  id: FormControl<IAppointment['id'] | NewAppointment['id']>;
  status: FormControl<IAppointment['status']>;
  year: FormControl<IAppointment['year']>;
  month: FormControl<IAppointment['month']>;
  day: FormControl<IAppointment['day']>;
  slot: FormControl<IAppointment['slot']>;
  appointmentUid: FormControl<IAppointment['appointmentUid']>;
  interviewer: FormControl<IAppointment['interviewer']>;
  interviewee: FormControl<IAppointment['interviewee']>;
};

export type AppointmentFormGroup = FormGroup<AppointmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppointmentFormService {
  createAppointmentFormGroup(appointment: AppointmentFormGroupInput = { id: null }): AppointmentFormGroup {
    const appointmentRawValue = {
      ...this.getFormDefaults(),
      ...appointment,
    };
    return new FormGroup<AppointmentFormGroupContent>({
      id: new FormControl(
        { value: appointmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      status: new FormControl(appointmentRawValue.status),
      year: new FormControl(appointmentRawValue.year),
      month: new FormControl(appointmentRawValue.month),
      day: new FormControl(appointmentRawValue.day),
      slot: new FormControl(appointmentRawValue.slot),
      appointmentUid: new FormControl(appointmentRawValue.appointmentUid),
      interviewer: new FormControl(appointmentRawValue.interviewer),
      interviewee: new FormControl(appointmentRawValue.interviewee),
    });
  }

  getAppointment(form: AppointmentFormGroup): IAppointment | NewAppointment {
    return form.getRawValue() as IAppointment | NewAppointment;
  }

  resetForm(form: AppointmentFormGroup, appointment: AppointmentFormGroupInput): void {
    const appointmentRawValue = { ...this.getFormDefaults(), ...appointment };
    form.reset(
      {
        ...appointmentRawValue,
        id: { value: appointmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AppointmentFormDefaults {
    return {
      id: null,
    };
  }
}
