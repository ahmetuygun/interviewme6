import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISlot, NewSlot } from '../slot.model';
import {IInterviewer} from "../../interviewer/interviewer.model";

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISlot for edit and NewSlotFormGroupInput for create.
 */
type SlotFormGroupInput = ISlot | PartialWithRequiredKeyOf<NewSlot>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISlot | NewSlot> = Omit<T, 'slot'> & {
  slot?: string | null;
};

type SlotFormRawValue = FormValueOf<ISlot>;

type NewSlotFormRawValue = FormValueOf<NewSlot>;

type SlotFormDefaults = Pick<NewSlot, 'id'  | 'isAvailable'>;

type SlotFormGroupContent = {
  id: FormControl<SlotFormRawValue['id'] | NewSlot['id']>;
  slot: FormControl<SlotFormRawValue['slot']>;
  isAvailable: FormControl<SlotFormRawValue['isAvailable']>;
  interviewer: FormControl<SlotFormRawValue['interviewer']>;
};

export type SlotFormGroup = FormGroup<SlotFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SlotFormService {
  createSlotFormGroup(slot: SlotFormGroupInput = { id: null }): SlotFormGroup {
    const slotRawValue = this.convertSlotToSlotRawValue({
      ...this.getFormDefaults(),
      ...slot,
    });
    return new FormGroup<SlotFormGroupContent>({
      id: new FormControl(
        { value: slotRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      slot: new FormControl(slotRawValue.slot),
      isAvailable: new FormControl(slotRawValue.isAvailable),
      interviewer: new FormControl(slotRawValue.interviewer),
    });
  }

  getSlot(form: SlotFormGroup): ISlot | NewSlot {
    return this.convertSlotRawValueToSlot(form.getRawValue() as SlotFormRawValue | NewSlotFormRawValue);
  }

  resetForm(form: SlotFormGroup, slot: SlotFormGroupInput): void {
    const slotRawValue = this.convertSlotToSlotRawValue({ ...this.getFormDefaults(), ...slot });
    form.reset(
      {
        ...slotRawValue,
        id: { value: slotRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SlotFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isAvailable: false,
    };
  }

  private convertSlotRawValueToSlot(rawSlot: SlotFormRawValue | NewSlotFormRawValue): {
    interviewer?: IInterviewer | null;
    isAvailable?: boolean | null;
    id: number | null;
    slot: undefined
  } {
    return {
      ...rawSlot,
      slot: undefined,
    };
  }

  private convertSlotToSlotRawValue(
    slot: ISlot | (Partial<NewSlot> & SlotFormDefaults),
  ): SlotFormRawValue | PartialWithRequiredKeyOf<NewSlotFormRawValue> {
    return {
      ...slot,
      slot : undefined,
    };
  }
}
