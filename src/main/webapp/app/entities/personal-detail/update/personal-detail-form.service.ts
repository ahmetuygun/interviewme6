import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPersonalDetail, NewPersonalDetail } from '../personal-detail.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonalDetail for edit and NewPersonalDetailFormGroupInput for create.
 */
type PersonalDetailFormGroupInput = IPersonalDetail | PartialWithRequiredKeyOf<NewPersonalDetail>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPersonalDetail | NewPersonalDetail> = Omit<T, 'dateOfBirth'> & {
  dateOfBirth?: string | null;
};

type PersonalDetailFormRawValue = FormValueOf<IPersonalDetail>;

type NewPersonalDetailFormRawValue = FormValueOf<NewPersonalDetail>;

type PersonalDetailFormDefaults = Pick<NewPersonalDetail, 'id' | 'dateOfBirth'>;

type PersonalDetailFormGroupContent = {
  id: FormControl<PersonalDetailFormRawValue['id'] | NewPersonalDetail['id']>;
  firstName: FormControl<PersonalDetailFormRawValue['firstName']>;
  lastName: FormControl<PersonalDetailFormRawValue['lastName']>;
  rawName: FormControl<PersonalDetailFormRawValue['rawName']>;
  middle: FormControl<PersonalDetailFormRawValue['middle']>;
  title: FormControl<PersonalDetailFormRawValue['title']>;
  prefix: FormControl<PersonalDetailFormRawValue['prefix']>;
  suffix: FormControl<PersonalDetailFormRawValue['suffix']>;
  formattedLocation: FormControl<PersonalDetailFormRawValue['formattedLocation']>;
  postalCode: FormControl<PersonalDetailFormRawValue['postalCode']>;
  region: FormControl<PersonalDetailFormRawValue['region']>;
  country: FormControl<PersonalDetailFormRawValue['country']>;
  countryCode: FormControl<PersonalDetailFormRawValue['countryCode']>;
  rawInputLocation: FormControl<PersonalDetailFormRawValue['rawInputLocation']>;
  street: FormControl<PersonalDetailFormRawValue['street']>;
  streetNumber: FormControl<PersonalDetailFormRawValue['streetNumber']>;
  apartmentNumber: FormControl<PersonalDetailFormRawValue['apartmentNumber']>;
  city: FormControl<PersonalDetailFormRawValue['city']>;
  selfSummary: FormControl<PersonalDetailFormRawValue['selfSummary']>;
  objective: FormControl<PersonalDetailFormRawValue['objective']>;
  dateOfBirth: FormControl<PersonalDetailFormRawValue['dateOfBirth']>;
  placeOfBirth: FormControl<PersonalDetailFormRawValue['placeOfBirth']>;
  phones: FormControl<PersonalDetailFormRawValue['phones']>;
  mails: FormControl<PersonalDetailFormRawValue['mails']>;
  urls: FormControl<PersonalDetailFormRawValue['urls']>;
  currentProfession: FormControl<PersonalDetailFormRawValue['currentProfession']>;
  gender: FormControl<PersonalDetailFormRawValue['gender']>;
  nationality: FormControl<PersonalDetailFormRawValue['nationality']>;
  martialStatus: FormControl<PersonalDetailFormRawValue['martialStatus']>;
  currentSalary: FormControl<PersonalDetailFormRawValue['currentSalary']>;
};

export type PersonalDetailFormGroup = FormGroup<PersonalDetailFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonalDetailFormService {
  createPersonalDetailFormGroup(personalDetail: PersonalDetailFormGroupInput = { id: null }): PersonalDetailFormGroup {
    const personalDetailRawValue = this.convertPersonalDetailToPersonalDetailRawValue({
      ...this.getFormDefaults(),
      ...personalDetail,
    });
    return new FormGroup<PersonalDetailFormGroupContent>({
      id: new FormControl(
        { value: personalDetailRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(personalDetailRawValue.firstName),
      lastName: new FormControl(personalDetailRawValue.lastName),
      rawName: new FormControl(personalDetailRawValue.rawName),
      middle: new FormControl(personalDetailRawValue.middle),
      title: new FormControl(personalDetailRawValue.title),
      prefix: new FormControl(personalDetailRawValue.prefix),
      suffix: new FormControl(personalDetailRawValue.suffix),
      formattedLocation: new FormControl(personalDetailRawValue.formattedLocation),
      postalCode: new FormControl(personalDetailRawValue.postalCode),
      region: new FormControl(personalDetailRawValue.region),
      country: new FormControl(personalDetailRawValue.country),
      countryCode: new FormControl(personalDetailRawValue.countryCode),
      rawInputLocation: new FormControl(personalDetailRawValue.rawInputLocation),
      street: new FormControl(personalDetailRawValue.street),
      streetNumber: new FormControl(personalDetailRawValue.streetNumber),
      apartmentNumber: new FormControl(personalDetailRawValue.apartmentNumber),
      city: new FormControl(personalDetailRawValue.city),
      selfSummary: new FormControl(personalDetailRawValue.selfSummary),
      objective: new FormControl(personalDetailRawValue.objective),
      dateOfBirth: new FormControl(personalDetailRawValue.dateOfBirth),
      placeOfBirth: new FormControl(personalDetailRawValue.placeOfBirth),
      phones: new FormControl(personalDetailRawValue.phones),
      mails: new FormControl(personalDetailRawValue.mails),
      urls: new FormControl(personalDetailRawValue.urls),
      currentProfession: new FormControl(personalDetailRawValue.currentProfession),
      gender: new FormControl(personalDetailRawValue.gender),
      nationality: new FormControl(personalDetailRawValue.nationality),
      martialStatus: new FormControl(personalDetailRawValue.martialStatus),
      currentSalary: new FormControl(personalDetailRawValue.currentSalary),
    });
  }

  getPersonalDetail(form: PersonalDetailFormGroup): IPersonalDetail | NewPersonalDetail {
    return this.convertPersonalDetailRawValueToPersonalDetail(
      form.getRawValue() as PersonalDetailFormRawValue | NewPersonalDetailFormRawValue,
    );
  }

  resetForm(form: PersonalDetailFormGroup, personalDetail: PersonalDetailFormGroupInput): void {
    const personalDetailRawValue = this.convertPersonalDetailToPersonalDetailRawValue({ ...this.getFormDefaults(), ...personalDetail });
    form.reset(
      {
        ...personalDetailRawValue,
        id: { value: personalDetailRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PersonalDetailFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateOfBirth: currentTime,
    };
  }

  private convertPersonalDetailRawValueToPersonalDetail(
    rawPersonalDetail: PersonalDetailFormRawValue | NewPersonalDetailFormRawValue,
  ): IPersonalDetail | NewPersonalDetail {
    return {
      ...rawPersonalDetail,
      dateOfBirth: dayjs(rawPersonalDetail.dateOfBirth, DATE_TIME_FORMAT),
    };
  }

  private convertPersonalDetailToPersonalDetailRawValue(
    personalDetail: IPersonalDetail | (Partial<NewPersonalDetail> & PersonalDetailFormDefaults),
  ): PersonalDetailFormRawValue | PartialWithRequiredKeyOf<NewPersonalDetailFormRawValue> {
    return {
      ...personalDetail,
      dateOfBirth: personalDetail.dateOfBirth ? personalDetail.dateOfBirth.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
