import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../work-experience-entry.test-samples';

import { WorkExperienceEntryFormService } from './work-experience-entry-form.service';

describe('WorkExperienceEntry Form Service', () => {
  let service: WorkExperienceEntryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkExperienceEntryFormService);
  });

  describe('Service methods', () => {
    describe('createWorkExperienceEntryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            company: expect.any(Object),
            description: expect.any(Object),
            industry: expect.any(Object),
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
            personalDetail: expect.any(Object),
          }),
        );
      });

      it('passing IWorkExperienceEntry should create a new form with FormGroup', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            company: expect.any(Object),
            description: expect.any(Object),
            industry: expect.any(Object),
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
            personalDetail: expect.any(Object),
          }),
        );
      });
    });

    describe('getWorkExperienceEntry', () => {
      it('should return NewWorkExperienceEntry for default WorkExperienceEntry initial value', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup(sampleWithNewData);

        const workExperienceEntry = service.getWorkExperienceEntry(formGroup) as any;

        expect(workExperienceEntry).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkExperienceEntry for empty WorkExperienceEntry initial value', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup();

        const workExperienceEntry = service.getWorkExperienceEntry(formGroup) as any;

        expect(workExperienceEntry).toMatchObject({});
      });

      it('should return IWorkExperienceEntry', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup(sampleWithRequiredData);

        const workExperienceEntry = service.getWorkExperienceEntry(formGroup) as any;

        expect(workExperienceEntry).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkExperienceEntry should not enable id FormControl', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkExperienceEntry should disable id FormControl', () => {
        const formGroup = service.createWorkExperienceEntryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
