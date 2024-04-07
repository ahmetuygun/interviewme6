import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../interview-note.test-samples';

import { InterviewNoteFormService } from './interview-note-form.service';

describe('InterviewNote Form Service', () => {
  let service: InterviewNoteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterviewNoteFormService);
  });

  describe('Service methods', () => {
    describe('createInterviewNoteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterviewNoteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            noteText: expect.any(Object),
            rating: expect.any(Object),
            actionItems: expect.any(Object),
            feedback: expect.any(Object),
          }),
        );
      });

      it('passing IInterviewNote should create a new form with FormGroup', () => {
        const formGroup = service.createInterviewNoteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            noteText: expect.any(Object),
            rating: expect.any(Object),
            actionItems: expect.any(Object),
            feedback: expect.any(Object),
          }),
        );
      });
    });

    describe('getInterviewNote', () => {
      it('should return NewInterviewNote for default InterviewNote initial value', () => {
        const formGroup = service.createInterviewNoteFormGroup(sampleWithNewData);

        const interviewNote = service.getInterviewNote(formGroup) as any;

        expect(interviewNote).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterviewNote for empty InterviewNote initial value', () => {
        const formGroup = service.createInterviewNoteFormGroup();

        const interviewNote = service.getInterviewNote(formGroup) as any;

        expect(interviewNote).toMatchObject({});
      });

      it('should return IInterviewNote', () => {
        const formGroup = service.createInterviewNoteFormGroup(sampleWithRequiredData);

        const interviewNote = service.getInterviewNote(formGroup) as any;

        expect(interviewNote).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterviewNote should not enable id FormControl', () => {
        const formGroup = service.createInterviewNoteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterviewNote should disable id FormControl', () => {
        const formGroup = service.createInterviewNoteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
