import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../interviewee.test-samples';

import { IntervieweeFormService } from './interviewee-form.service';

describe('Interviewee Form Service', () => {
  let service: IntervieweeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntervieweeFormService);
  });

  describe('Service methods', () => {
    describe('createIntervieweeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIntervieweeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            photo: expect.any(Object),
            internalUser: expect.any(Object),
            personalDetail: expect.any(Object),
            membership: expect.any(Object),
          }),
        );
      });

      it('passing IInterviewee should create a new form with FormGroup', () => {
        const formGroup = service.createIntervieweeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            photo: expect.any(Object),
            internalUser: expect.any(Object),
            personalDetail: expect.any(Object),
            membership: expect.any(Object),
          }),
        );
      });
    });

    describe('getInterviewee', () => {
      it('should return NewInterviewee for default Interviewee initial value', () => {
        const formGroup = service.createIntervieweeFormGroup(sampleWithNewData);

        const interviewee = service.getInterviewee(formGroup) as any;

        expect(interviewee).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterviewee for empty Interviewee initial value', () => {
        const formGroup = service.createIntervieweeFormGroup();

        const interviewee = service.getInterviewee(formGroup) as any;

        expect(interviewee).toMatchObject({});
      });

      it('should return IInterviewee', () => {
        const formGroup = service.createIntervieweeFormGroup(sampleWithRequiredData);

        const interviewee = service.getInterviewee(formGroup) as any;

        expect(interviewee).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterviewee should not enable id FormControl', () => {
        const formGroup = service.createIntervieweeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterviewee should disable id FormControl', () => {
        const formGroup = service.createIntervieweeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
