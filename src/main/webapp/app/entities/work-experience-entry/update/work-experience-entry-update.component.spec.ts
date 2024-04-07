import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { PersonalDetailService } from 'app/entities/personal-detail/service/personal-detail.service';
import { WorkExperienceEntryService } from '../service/work-experience-entry.service';
import { IWorkExperienceEntry } from '../work-experience-entry.model';
import { WorkExperienceEntryFormService } from './work-experience-entry-form.service';

import { WorkExperienceEntryUpdateComponent } from './work-experience-entry-update.component';

describe('WorkExperienceEntry Management Update Component', () => {
  let comp: WorkExperienceEntryUpdateComponent;
  let fixture: ComponentFixture<WorkExperienceEntryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workExperienceEntryFormService: WorkExperienceEntryFormService;
  let workExperienceEntryService: WorkExperienceEntryService;
  let personalDetailService: PersonalDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), WorkExperienceEntryUpdateComponent],
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
      .overrideTemplate(WorkExperienceEntryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkExperienceEntryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workExperienceEntryFormService = TestBed.inject(WorkExperienceEntryFormService);
    workExperienceEntryService = TestBed.inject(WorkExperienceEntryService);
    personalDetailService = TestBed.inject(PersonalDetailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PersonalDetail query and add missing value', () => {
      const workExperienceEntry: IWorkExperienceEntry = { id: 456 };
      const personalDetail: IPersonalDetail = { id: 570 };
      workExperienceEntry.personalDetail = personalDetail;

      const personalDetailCollection: IPersonalDetail[] = [{ id: 22587 }];
      jest.spyOn(personalDetailService, 'query').mockReturnValue(of(new HttpResponse({ body: personalDetailCollection })));
      const additionalPersonalDetails = [personalDetail];
      const expectedCollection: IPersonalDetail[] = [...additionalPersonalDetails, ...personalDetailCollection];
      jest.spyOn(personalDetailService, 'addPersonalDetailToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workExperienceEntry });
      comp.ngOnInit();

      expect(personalDetailService.query).toHaveBeenCalled();
      expect(personalDetailService.addPersonalDetailToCollectionIfMissing).toHaveBeenCalledWith(
        personalDetailCollection,
        ...additionalPersonalDetails.map(expect.objectContaining),
      );
      expect(comp.personalDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const workExperienceEntry: IWorkExperienceEntry = { id: 456 };
      const personalDetail: IPersonalDetail = { id: 28291 };
      workExperienceEntry.personalDetail = personalDetail;

      activatedRoute.data = of({ workExperienceEntry });
      comp.ngOnInit();

      expect(comp.personalDetailsSharedCollection).toContain(personalDetail);
      expect(comp.workExperienceEntry).toEqual(workExperienceEntry);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkExperienceEntry>>();
      const workExperienceEntry = { id: 123 };
      jest.spyOn(workExperienceEntryFormService, 'getWorkExperienceEntry').mockReturnValue(workExperienceEntry);
      jest.spyOn(workExperienceEntryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workExperienceEntry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workExperienceEntry }));
      saveSubject.complete();

      // THEN
      expect(workExperienceEntryFormService.getWorkExperienceEntry).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workExperienceEntryService.update).toHaveBeenCalledWith(expect.objectContaining(workExperienceEntry));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkExperienceEntry>>();
      const workExperienceEntry = { id: 123 };
      jest.spyOn(workExperienceEntryFormService, 'getWorkExperienceEntry').mockReturnValue({ id: null });
      jest.spyOn(workExperienceEntryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workExperienceEntry: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workExperienceEntry }));
      saveSubject.complete();

      // THEN
      expect(workExperienceEntryFormService.getWorkExperienceEntry).toHaveBeenCalled();
      expect(workExperienceEntryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkExperienceEntry>>();
      const workExperienceEntry = { id: 123 };
      jest.spyOn(workExperienceEntryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workExperienceEntry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workExperienceEntryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePersonalDetail', () => {
      it('Should forward to personalDetailService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personalDetailService, 'comparePersonalDetail');
        comp.comparePersonalDetail(entity, entity2);
        expect(personalDetailService.comparePersonalDetail).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
