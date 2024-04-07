import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { InterviewerService } from 'app/entities/interviewer/service/interviewer.service';
import { SlotService } from '../service/slot.service';
import { ISlot } from '../slot.model';
import { SlotFormService } from './slot-form.service';

import { SlotUpdateComponent } from './slot-update.component';

describe('Slot Management Update Component', () => {
  let comp: SlotUpdateComponent;
  let fixture: ComponentFixture<SlotUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let slotFormService: SlotFormService;
  let slotService: SlotService;
  let interviewerService: InterviewerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SlotUpdateComponent],
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
      .overrideTemplate(SlotUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SlotUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    slotFormService = TestBed.inject(SlotFormService);
    slotService = TestBed.inject(SlotService);
    interviewerService = TestBed.inject(InterviewerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Interviewer query and add missing value', () => {
      const slot: ISlot = { id: 456 };
      const interviewer: IInterviewer = { id: 12440 };
      slot.interviewer = interviewer;

      const interviewerCollection: IInterviewer[] = [{ id: 26796 }];
      jest.spyOn(interviewerService, 'query').mockReturnValue(of(new HttpResponse({ body: interviewerCollection })));
      const additionalInterviewers = [interviewer];
      const expectedCollection: IInterviewer[] = [...additionalInterviewers, ...interviewerCollection];
      jest.spyOn(interviewerService, 'addInterviewerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ slot });
      comp.ngOnInit();

      expect(interviewerService.query).toHaveBeenCalled();
      expect(interviewerService.addInterviewerToCollectionIfMissing).toHaveBeenCalledWith(
        interviewerCollection,
        ...additionalInterviewers.map(expect.objectContaining),
      );
      expect(comp.interviewersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const slot: ISlot = { id: 456 };
      const interviewer: IInterviewer = { id: 1605 };
      slot.interviewer = interviewer;

      activatedRoute.data = of({ slot });
      comp.ngOnInit();

      expect(comp.interviewersSharedCollection).toContain(interviewer);
      expect(comp.slot).toEqual(slot);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISlot>>();
      const slot = { id: 123 };
      jest.spyOn(slotFormService, 'getSlot').mockReturnValue(slot);
      jest.spyOn(slotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ slot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: slot }));
      saveSubject.complete();

      // THEN
      expect(slotFormService.getSlot).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(slotService.update).toHaveBeenCalledWith(expect.objectContaining(slot));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISlot>>();
      const slot = { id: 123 };
      jest.spyOn(slotFormService, 'getSlot').mockReturnValue({ id: null });
      jest.spyOn(slotService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ slot: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: slot }));
      saveSubject.complete();

      // THEN
      expect(slotFormService.getSlot).toHaveBeenCalled();
      expect(slotService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISlot>>();
      const slot = { id: 123 };
      jest.spyOn(slotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ slot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(slotService.update).toHaveBeenCalled();
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
