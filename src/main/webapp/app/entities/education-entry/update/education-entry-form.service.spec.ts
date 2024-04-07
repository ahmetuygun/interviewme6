import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../education-entry.test-samples';

import { EducationEntryFormService } from './education-entry-form.service';

describe('EducationEntry Form Service', () => {
  let service: EducationEntryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EducationEntryFormService);
  });

  describe('Service methods', () => {
    describe('createEducationEntryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEducationEntryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            establishment: expect.any(Object),
            description: expect.any(Object),
            gpa: expect.any(Object),
            accreditation: expect.any(Object),
            personalDetail: expect.any(Object),
          }),
        );
      });

      it('passing IEducationEntry should create a new form with FormGroup', () => {
        const formGroup = service.createEducationEntryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            establishment: expect.any(Object),
            description: expect.any(Object),
            gpa: expect.any(Object),
            accreditation: expect.any(Object),
            personalDetail: expect.any(Object),
          }),
        );
      });
    });

    describe('getEducationEntry', () => {
      it('should return NewEducationEntry for default EducationEntry initial value', () => {
        const formGroup = service.createEducationEntryFormGroup(sampleWithNewData);

        const educationEntry = service.getEducationEntry(formGroup) as any;

        expect(educationEntry).toMatchObject(sampleWithNewData);
      });

      it('should return NewEducationEntry for empty EducationEntry initial value', () => {
        const formGroup = service.createEducationEntryFormGroup();

        const educationEntry = service.getEducationEntry(formGroup) as any;

        expect(educationEntry).toMatchObject({});
      });

      it('should return IEducationEntry', () => {
        const formGroup = service.createEducationEntryFormGroup(sampleWithRequiredData);

        const educationEntry = service.getEducationEntry(formGroup) as any;

        expect(educationEntry).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEducationEntry should not enable id FormControl', () => {
        const formGroup = service.createEducationEntryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEducationEntry should disable id FormControl', () => {
        const formGroup = service.createEducationEntryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
