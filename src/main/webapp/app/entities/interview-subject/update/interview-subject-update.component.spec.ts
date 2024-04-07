import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { InterviewerService } from 'app/entities/interviewer/service/interviewer.service';
import { InterviewSubjectService } from '../service/interview-subject.service';
import { IInterviewSubject } from '../interview-subject.model';
import { InterviewSubjectFormService } from './interview-subject-form.service';

import { InterviewSubjectUpdateComponent } from './interview-subject-update.component';

describe('InterviewSubject Management Update Component', () => {
  let comp: InterviewSubjectUpdateComponent;
  let fixture: ComponentFixture<InterviewSubjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let interviewSubjectFormService: InterviewSubjectFormService;
  let interviewSubjectService: InterviewSubjectService;
  let interviewerService: InterviewerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), InterviewSubjectUpdateComponent],
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
      .overrideTemplate(InterviewSubjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterviewSubjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interviewSubjectFormService = TestBed.inject(InterviewSubjectFormService);
    interviewSubjectService = TestBed.inject(InterviewSubjectService);
    interviewerService = TestBed.inject(InterviewerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Interviewer query and add missing value', () => {
      const interviewSubject: IInterviewSubject = { id: 456 };
      const interviewer: IInterviewer = { id: 18734 };
      interviewSubject.interviewer = interviewer;

      const interviewerCollection: IInterviewer[] = [{ id: 12707 }];
      jest.spyOn(interviewerService, 'query').mockReturnValue(of(new HttpResponse({ body: interviewerCollection })));
      const additionalInterviewers = [interviewer];
      const expectedCollection: IInterviewer[] = [...additionalInterviewers, ...interviewerCollection];
      jest.spyOn(interviewerService, 'addInterviewerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interviewSubject });
      comp.ngOnInit();

      expect(interviewerService.query).toHaveBeenCalled();
      expect(interviewerService.addInterviewerToCollectionIfMissing).toHaveBeenCalledWith(
        interviewerCollection,
        ...additionalInterviewers.map(expect.objectContaining),
      );
      expect(comp.interviewersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const interviewSubject: IInterviewSubject = { id: 456 };
      const interviewer: IInterviewer = { id: 13636 };
      interviewSubject.interviewer = interviewer;

      activatedRoute.data = of({ interviewSubject });
      comp.ngOnInit();

      expect(comp.interviewersSharedCollection).toContain(interviewer);
      expect(comp.interviewSubject).toEqual(interviewSubject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewSubject>>();
      const interviewSubject = { id: 123 };
      jest.spyOn(interviewSubjectFormService, 'getInterviewSubject').mockReturnValue(interviewSubject);
      jest.spyOn(interviewSubjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewSubject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewSubject }));
      saveSubject.complete();

      // THEN
      expect(interviewSubjectFormService.getInterviewSubject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interviewSubjectService.update).toHaveBeenCalledWith(expect.objectContaining(interviewSubject));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewSubject>>();
      const interviewSubject = { id: 123 };
      jest.spyOn(interviewSubjectFormService, 'getInterviewSubject').mockReturnValue({ id: null });
      jest.spyOn(interviewSubjectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewSubject: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewSubject }));
      saveSubject.complete();

      // THEN
      expect(interviewSubjectFormService.getInterviewSubject).toHaveBeenCalled();
      expect(interviewSubjectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewSubject>>();
      const interviewSubject = { id: 123 };
      jest.spyOn(interviewSubjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewSubject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interviewSubjectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareInterviewer', () => {
      it('Should forward to interviewerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(interviewerService, 'compareInterviewer');
        comp.compareInterviewer(entity, entity2);
        expect(interviewerService.compareInterviewer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
