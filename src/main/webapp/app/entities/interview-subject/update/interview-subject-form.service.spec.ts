import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../interview-subject.test-samples';

import { InterviewSubjectFormService } from './interview-subject-form.service';

describe('InterviewSubject Form Service', () => {
  let service: InterviewSubjectFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterviewSubjectFormService);
  });

  describe('Service methods', () => {
    describe('createInterviewSubjectFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterviewSubjectFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            parent: expect.any(Object),
            interviewer: expect.any(Object),
          }),
        );
      });

      it('passing IInterviewSubject should create a new form with FormGroup', () => {
        const formGroup = service.createInterviewSubjectFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            parent: expect.any(Object),
            interviewer: expect.any(Object),
          }),
        );
      });
    });

    describe('getInterviewSubject', () => {
      it('should return NewInterviewSubject for default InterviewSubject initial value', () => {
        const formGroup = service.createInterviewSubjectFormGroup(sampleWithNewData);

        const interviewSubject = service.getInterviewSubject(formGroup) as any;

        expect(interviewSubject).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterviewSubject for empty InterviewSubject initial value', () => {
        const formGroup = service.createInterviewSubjectFormGroup();

        const interviewSubject = service.getInterviewSubject(formGroup) as any;

        expect(interviewSubject).toMatchObject({});
      });

      it('should return IInterviewSubject', () => {
        const formGroup = service.createInterviewSubjectFormGroup(sampleWithRequiredData);

        const interviewSubject = service.getInterviewSubject(formGroup) as any;

        expect(interviewSubject).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterviewSubject should not enable id FormControl', () => {
        const formGroup = service.createInterviewSubjectFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterviewSubject should disable id FormControl', () => {
        const formGroup = service.createInterviewSubjectFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
