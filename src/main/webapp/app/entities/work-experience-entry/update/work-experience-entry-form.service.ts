import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWorkExperienceEntry, NewWorkExperienceEntry } from '../work-experience-entry.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkExperienceEntry for edit and NewWorkExperienceEntryFormGroupInput for create.
 */
type WorkExperienceEntryFormGroupInput = IWorkExperienceEntry | PartialWithRequiredKeyOf<NewWorkExperienceEntry>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IWorkExperienceEntry | NewWorkExperienceEntry> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type WorkExperienceEntryFormRawValue = FormValueOf<IWorkExperienceEntry>;

type NewWorkExperienceEntryFormRawValue = FormValueOf<NewWorkExperienceEntry>;

type WorkExperienceEntryFormDefaults = Pick<NewWorkExperienceEntry, 'id' | 'startDate' | 'endDate'>;

type WorkExperienceEntryFormGroupContent = {
  id: FormControl<WorkExperienceEntryFormRawValue['id'] | NewWorkExperienceEntry['id']>;
  title: FormControl<WorkExperienceEntryFormRawValue['title']>;
  startDate: FormControl<WorkExperienceEntryFormRawValue['startDate']>;
  endDate: FormControl<WorkExperienceEntryFormRawValue['endDate']>;
  company: FormControl<WorkExperienceEntryFormRawValue['company']>;
  description: FormControl<WorkExperienceEntryFormRawValue['description']>;
  industry: FormControl<WorkExperienceEntryFormRawValue['industry']>;
  formattedLocation: FormControl<WorkExperienceEntryFormRawValue['formattedLocation']>;
  postalCode: FormControl<WorkExperienceEntryFormRawValue['postalCode']>;
  region: FormControl<WorkExperienceEntryFormRawValue['region']>;
  country: FormControl<WorkExperienceEntryFormRawValue['country']>;
  countryCode: FormControl<WorkExperienceEntryFormRawValue['countryCode']>;
  rawInputLocation: FormControl<WorkExperienceEntryFormRawValue['rawInputLocation']>;
  street: FormControl<WorkExperienceEntryFormRawValue['street']>;
  streetNumber: FormControl<WorkExperienceEntryFormRawValue['streetNumber']>;
  apartmentNumber: FormControl<WorkExperienceEntryFormRawValue['apartmentNumber']>;
  city: FormControl<WorkExperienceEntryFormRawValue['city']>;
  personalDetail: FormControl<WorkExperienceEntryFormRawValue['personalDetail']>;
};

export type WorkExperienceEntryFormGroup = FormGroup<WorkExperienceEntryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkExperienceEntryFormService {
  createWorkExperienceEntryFormGroup(workExperienceEntry: WorkExperienceEntryFormGroupInput = { id: null }): WorkExperienceEntryFormGroup {
    const workExperienceEntryRawValue = this.convertWorkExperienceEntryToWorkExperienceEntryRawValue({
      ...this.getFormDefaults(),
      ...workExperienceEntry,
    });
    return new FormGroup<WorkExperienceEntryFormGroupContent>({
      id: new FormControl(
        { value: workExperienceEntryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(workExperienceEntryRawValue.title),
      startDate: new FormControl(workExperienceEntryRawValue.startDate),
      endDate: new FormControl(workExperienceEntryRawValue.endDate),
      company: new FormControl(workExperienceEntryRawValue.company),
      description: new FormControl(workExperienceEntryRawValue.description),
      industry: new FormControl(workExperienceEntryRawValue.industry),
      formattedLocation: new FormControl(workExperienceEntryRawValue.formattedLocation),
      postalCode: new FormControl(workExperienceEntryRawValue.postalCode),
      region: new FormControl(workExperienceEntryRawValue.region),
      country: new FormControl(workExperienceEntryRawValue.country),
      countryCode: new FormControl(workExperienceEntryRawValue.countryCode),
      rawInputLocation: new FormControl(workExperienceEntryRawValue.rawInputLocation),
      street: new FormControl(workExperienceEntryRawValue.street),
      streetNumber: new FormControl(workExperienceEntryRawValue.streetNumber),
      apartmentNumber: new FormControl(workExperienceEntryRawValue.apartmentNumber),
      city: new FormControl(workExperienceEntryRawValue.city),
      personalDetail: new FormControl(workExperienceEntryRawValue.personalDetail),
    });
  }

  getWorkExperienceEntry(form: WorkExperienceEntryFormGroup): IWorkExperienceEntry | NewWorkExperienceEntry {
    return this.convertWorkExperienceEntryRawValueToWorkExperienceEntry(
      form.getRawValue() as WorkExperienceEntryFormRawValue | NewWorkExperienceEntryFormRawValue,
    );
  }

  resetForm(form: WorkExperienceEntryFormGroup, workExperienceEntry: WorkExperienceEntryFormGroupInput): void {
    const workExperienceEntryRawValue = this.convertWorkExperienceEntryToWorkExperienceEntryRawValue({
      ...this.getFormDefaults(),
      ...workExperienceEntry,
    });
    form.reset(
      {
        ...workExperienceEntryRawValue,
        id: { value: workExperienceEntryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): WorkExperienceEntryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
    };
  }

  private convertWorkExperienceEntryRawValueToWorkExperienceEntry(
    rawWorkExperienceEntry: WorkExperienceEntryFormRawValue | NewWorkExperienceEntryFormRawValue,
  ): IWorkExperienceEntry | NewWorkExperienceEntry {
    return {
      ...rawWorkExperienceEntry,
      startDate: dayjs(rawWorkExperienceEntry.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawWorkExperienceEntry.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertWorkExperienceEntryToWorkExperienceEntryRawValue(
    workExperienceEntry: IWorkExperienceEntry | (Partial<NewWorkExperienceEntry> & WorkExperienceEntryFormDefaults),
  ): WorkExperienceEntryFormRawValue | PartialWithRequiredKeyOf<NewWorkExperienceEntryFormRawValue> {
    return {
      ...workExperienceEntry,
      startDate: workExperienceEntry.startDate ? workExperienceEntry.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: workExperienceEntry.endDate ? workExperienceEntry.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
