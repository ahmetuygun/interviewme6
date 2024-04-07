import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { PersonalDetailService } from 'app/entities/personal-detail/service/personal-detail.service';
import { EducationEntryService } from '../service/education-entry.service';
import { IEducationEntry } from '../education-entry.model';
import { EducationEntryFormService } from './education-entry-form.service';

import { EducationEntryUpdateComponent } from './education-entry-update.component';

describe('EducationEntry Management Update Component', () => {
  let comp: EducationEntryUpdateComponent;
  let fixture: ComponentFixture<EducationEntryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let educationEntryFormService: EducationEntryFormService;
  let educationEntryService: EducationEntryService;
  let personalDetailService: PersonalDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EducationEntryUpdateComponent],
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
      .overrideTemplate(EducationEntryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EducationEntryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    educationEntryFormService = TestBed.inject(EducationEntryFormService);
    educationEntryService = TestBed.inject(EducationEntryService);
    personalDetailService = TestBed.inject(PersonalDetailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PersonalDetail query and add missing value', () => {
      const educationEntry: IEducationEntry = { id: 456 };
      const personalDetail: IPersonalDetail = { id: 7512 };
      educationEntry.personalDetail = personalDetail;

      const personalDetailCollection: IPersonalDetail[] = [{ id: 17239 }];
      jest.spyOn(personalDetailService, 'query').mockReturnValue(of(new HttpResponse({ body: personalDetailCollection })));
      const additionalPersonalDetails = [personalDetail];
      const expectedCollection: IPersonalDetail[] = [...additionalPersonalDetails, ...personalDetailCollection];
      jest.spyOn(personalDetailService, 'addPersonalDetailToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ educationEntry });
      comp.ngOnInit();

      expect(personalDetailService.query).toHaveBeenCalled();
      expect(personalDetailService.addPersonalDetailToCollectionIfMissing).toHaveBeenCalledWith(
        personalDetailCollection,
        ...additionalPersonalDetails.map(expect.objectContaining),
      );
      expect(comp.personalDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const educationEntry: IEducationEntry = { id: 456 };
      const personalDetail: IPersonalDetail = { id: 25847 };
      educationEntry.personalDetail = personalDetail;

      activatedRoute.data = of({ educationEntry });
      comp.ngOnInit();

      expect(comp.personalDetailsSharedCollection).toContain(personalDetail);
      expect(comp.educationEntry).toEqual(educationEntry);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEducationEntry>>();
      const educationEntry = { id: 123 };
      jest.spyOn(educationEntryFormService, 'getEducationEntry').mockReturnValue(educationEntry);
      jest.spyOn(educationEntryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationEntry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: educationEntry }));
      saveSubject.complete();

      // THEN
      expect(educationEntryFormService.getEducationEntry).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(educationEntryService.update).toHaveBeenCalledWith(expect.objectContaining(educationEntry));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEducationEntry>>();
      const educationEntry = { id: 123 };
      jest.spyOn(educationEntryFormService, 'getEducationEntry').mockReturnValue({ id: null });
      jest.spyOn(educationEntryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationEntry: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: educationEntry }));
      saveSubject.complete();

      // THEN
      expect(educationEntryFormService.getEducationEntry).toHaveBeenCalled();
      expect(educationEntryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEducationEntry>>();
      const educationEntry = { id: 123 };
      jest.spyOn(educationEntryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationEntry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(educationEntryService.update).toHaveBeenCalled();
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
