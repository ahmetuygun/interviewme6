import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMembershipLevel, NewMembershipLevel } from '../membership-level.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMembershipLevel for edit and NewMembershipLevelFormGroupInput for create.
 */
type MembershipLevelFormGroupInput = IMembershipLevel | PartialWithRequiredKeyOf<NewMembershipLevel>;

type MembershipLevelFormDefaults = Pick<NewMembershipLevel, 'id'>;

type MembershipLevelFormGroupContent = {
  id: FormControl<IMembershipLevel['id'] | NewMembershipLevel['id']>;
  name: FormControl<IMembershipLevel['name']>;
  description: FormControl<IMembershipLevel['description']>;
  sessionAmount: FormControl<IMembershipLevel['sessionAmount']>;
  price: FormControl<IMembershipLevel['price']>;
};

export type MembershipLevelFormGroup = FormGroup<MembershipLevelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MembershipLevelFormService {
  createMembershipLevelFormGroup(membershipLevel: MembershipLevelFormGroupInput = { id: null }): MembershipLevelFormGroup {
    const membershipLevelRawValue = {
      ...this.getFormDefaults(),
      ...membershipLevel,
    };
    return new FormGroup<MembershipLevelFormGroupContent>({
      id: new FormControl(
        { value: membershipLevelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(membershipLevelRawValue.name),
      description: new FormControl(membershipLevelRawValue.description),
      sessionAmount: new FormControl(membershipLevelRawValue.sessionAmount),
      price: new FormControl(membershipLevelRawValue.price),
    });
  }

  getMembershipLevel(form: MembershipLevelFormGroup): IMembershipLevel | NewMembershipLevel {
    return form.getRawValue() as IMembershipLevel | NewMembershipLevel;
  }

  resetForm(form: MembershipLevelFormGroup, membershipLevel: MembershipLevelFormGroupInput): void {
    const membershipLevelRawValue = { ...this.getFormDefaults(), ...membershipLevel };
    form.reset(
      {
        ...membershipLevelRawValue,
        id: { value: membershipLevelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MembershipLevelFormDefaults {
    return {
      id: null,
    };
  }
}
