import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEducationEntry, NewEducationEntry } from '../education-entry.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEducationEntry for edit and NewEducationEntryFormGroupInput for create.
 */
type EducationEntryFormGroupInput = IEducationEntry | PartialWithRequiredKeyOf<NewEducationEntry>;

type EducationEntryFormDefaults = Pick<NewEducationEntry, 'id'>;

type EducationEntryFormGroupContent = {
  id: FormControl<IEducationEntry['id'] | NewEducationEntry['id']>;
  title: FormControl<IEducationEntry['title']>;
  startDate: FormControl<IEducationEntry['startDate']>;
  endDate: FormControl<IEducationEntry['endDate']>;
  establishment: FormControl<IEducationEntry['establishment']>;
  description: FormControl<IEducationEntry['description']>;
  gpa: FormControl<IEducationEntry['gpa']>;
  accreditation: FormControl<IEducationEntry['accreditation']>;
  personalDetail: FormControl<IEducationEntry['personalDetail']>;
};

export type EducationEntryFormGroup = FormGroup<EducationEntryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EducationEntryFormService {
  createEducationEntryFormGroup(educationEntry: EducationEntryFormGroupInput = { id: null }): EducationEntryFormGroup {
    const educationEntryRawValue = {
      ...this.getFormDefaults(),
      ...educationEntry,
    };
    return new FormGroup<EducationEntryFormGroupContent>({
      id: new FormControl(
        { value: educationEntryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(educationEntryRawValue.title),
      startDate: new FormControl(educationEntryRawValue.startDate),
      endDate: new FormControl(educationEntryRawValue.endDate),
      establishment: new FormControl(educationEntryRawValue.establishment),
      description: new FormControl(educationEntryRawValue.description),
      gpa: new FormControl(educationEntryRawValue.gpa),
      accreditation: new FormControl(educationEntryRawValue.accreditation),
      personalDetail: new FormControl(educationEntryRawValue.personalDetail),
    });
  }

  getEducationEntry(form: EducationEntryFormGroup): IEducationEntry | NewEducationEntry {
    return form.getRawValue() as IEducationEntry | NewEducationEntry;
  }

  resetForm(form: EducationEntryFormGroup, educationEntry: EducationEntryFormGroupInput): void {
    const educationEntryRawValue = { ...this.getFormDefaults(), ...educationEntry };
    form.reset(
      {
        ...educationEntryRawValue,
        id: { value: educationEntryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EducationEntryFormDefaults {
    return {
      id: null,
    };
  }
}
