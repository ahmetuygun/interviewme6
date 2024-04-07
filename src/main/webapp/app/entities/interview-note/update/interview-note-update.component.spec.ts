import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InterviewNoteService } from '../service/interview-note.service';
import { IInterviewNote } from '../interview-note.model';
import { InterviewNoteFormService } from './interview-note-form.service';

import { InterviewNoteUpdateComponent } from './interview-note-update.component';

describe('InterviewNote Management Update Component', () => {
  let comp: InterviewNoteUpdateComponent;
  let fixture: ComponentFixture<InterviewNoteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let interviewNoteFormService: InterviewNoteFormService;
  let interviewNoteService: InterviewNoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), InterviewNoteUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(InterviewNoteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterviewNoteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interviewNoteFormService = TestBed.inject(InterviewNoteFormService);
    interviewNoteService = TestBed.inject(InterviewNoteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const interviewNote: IInterviewNote = { id: 456 };

      activatedRoute.data = of({ interviewNote });
      comp.ngOnInit();

      expect(comp.interviewNote).toEqual(interviewNote);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewNote>>();
      const interviewNote = { id: 123 };
      jest.spyOn(interviewNoteFormService, 'getInterviewNote').mockReturnValue(interviewNote);
      jest.spyOn(interviewNoteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewNote }));
      saveSubject.complete();

      // THEN
      expect(interviewNoteFormService.getInterviewNote).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interviewNoteService.update).toHaveBeenCalledWith(expect.objectContaining(interviewNote));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewNote>>();
      const interviewNote = { id: 123 };
      jest.spyOn(interviewNoteFormService, 'getInterviewNote').mockReturnValue({ id: null });
      jest.spyOn(interviewNoteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewNote: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewNote }));
      saveSubject.complete();

      // THEN
      expect(interviewNoteFormService.getInterviewNote).toHaveBeenCalled();
      expect(interviewNoteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewNote>>();
      const interviewNote = { id: 123 };
      jest.spyOn(interviewNoteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewNote });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interviewNoteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
