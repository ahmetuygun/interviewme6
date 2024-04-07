import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../interviewer.test-samples';

import { InterviewerFormService } from './interviewer-form.service';

describe('Interviewer Form Service', () => {
  let service: InterviewerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterviewerFormService);
  });

  describe('Service methods', () => {
    describe('createInterviewerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterviewerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            photo: expect.any(Object),
            internalUser: expect.any(Object),
            personalDetail: expect.any(Object),
          }),
        );
      });

      it('passing IInterviewer should create a new form with FormGroup', () => {
        const formGroup = service.createInterviewerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            photo: expect.any(Object),
            internalUser: expect.any(Object),
            personalDetail: expect.any(Object),
          }),
        );
      });
    });

    describe('getInterviewer', () => {
      it('should return NewInterviewer for default Interviewer initial value', () => {
        const formGroup = service.createInterviewerFormGroup(sampleWithNewData);

        const interviewer = service.getInterviewer(formGroup) as any;

        expect(interviewer).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterviewer for empty Interviewer initial value', () => {
        const formGroup = service.createInterviewerFormGroup();

        const interviewer = service.getInterviewer(formGroup) as any;

        expect(interviewer).toMatchObject({});
      });

      it('should return IInterviewer', () => {
        const formGroup = service.createInterviewerFormGroup(sampleWithRequiredData);

        const interviewer = service.getInterviewer(formGroup) as any;

        expect(interviewer).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterviewer should not enable id FormControl', () => {
        const formGroup = service.createInterviewerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterviewer should disable id FormControl', () => {
        const formGroup = service.createInterviewerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
