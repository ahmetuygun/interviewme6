import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../personal-detail.test-samples';

import { PersonalDetailFormService } from './personal-detail-form.service';

describe('PersonalDetail Form Service', () => {
  let service: PersonalDetailFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalDetailFormService);
  });

  describe('Service methods', () => {
    describe('createPersonalDetailFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonalDetailFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cv: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            rawName: expect.any(Object),
            middle: expect.any(Object),
            title: expect.any(Object),
            prefix: expect.any(Object),
            suffix: expect.any(Object),
            formattedLocation: expect.any(Object),
            postalCode: expect.any(Object),
            region: expect.any(Object),
            country: expect.any(Object),
            countryCode: expect.any(Object),
            rawInputLocation: expect.any(Object),
            street: expect.any(Object),
            streetNumber: expect.any(Object),
            apartmentNumber: expect.any(Object),
            city: expect.any(Object),
            selfSummary: expect.any(Object),
            objective: expect.any(Object),
            dateOfBirth: expect.any(Object),
            placeOfBirth: expect.any(Object),
            phones: expect.any(Object),
            mails: expect.any(Object),
            urls: expect.any(Object),
            currentProfession: expect.any(Object),
            gender: expect.any(Object),
            nationality: expect.any(Object),
            martialStatus: expect.any(Object),
            currentSalary: expect.any(Object),
          }),
        );
      });

      it('passing IPersonalDetail should create a new form with FormGroup', () => {
        const formGroup = service.createPersonalDetailFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cv: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            rawName: expect.any(Object),
            middle: expect.any(Object),
            title: expect.any(Object),
            prefix: expect.any(Object),
            suffix: expect.any(Object),
            formattedLocation: expect.any(Object),
            postalCode: expect.any(Object),
            region: expect.any(Object),
            country: expect.any(Object),
            countryCode: expect.any(Object),
            rawInputLocation: expect.any(Object),
            street: expect.any(Object),
            streetNumber: expect.any(Object),
            apartmentNumber: expect.any(Object),
            city: expect.any(Object),
            selfSummary: expect.any(Object),
            objective: expect.any(Object),
            dateOfBirth: expect.any(Object),
            placeOfBirth: expect.any(Object),
            phones: expect.any(Object),
            mails: expect.any(Object),
            urls: expect.any(Object),
            currentProfession: expect.any(Object),
            gender: expect.any(Object),
            nationality: expect.any(Object),
            martialStatus: expect.any(Object),
            currentSalary: expect.any(Object),
          }),
        );
      });
    });

    describe('getPersonalDetail', () => {
      it('should return NewPersonalDetail for default PersonalDetail initial value', () => {
        const formGroup = service.createPersonalDetailFormGroup(sampleWithNewData);

        const personalDetail = service.getPersonalDetail(formGroup) as any;

        expect(personalDetail).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonalDetail for empty PersonalDetail initial value', () => {
        const formGroup = service.createPersonalDetailFormGroup();

        const personalDetail = service.getPersonalDetail(formGroup) as any;

        expect(personalDetail).toMatchObject({});
      });

      it('should return IPersonalDetail', () => {
        const formGroup = service.createPersonalDetailFormGroup(sampleWithRequiredData);

        const personalDetail = service.getPersonalDetail(formGroup) as any;

        expect(personalDetail).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonalDetail should not enable id FormControl', () => {
        const formGroup = service.createPersonalDetailFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonalDetail should disable id FormControl', () => {
        const formGroup = service.createPersonalDetailFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
