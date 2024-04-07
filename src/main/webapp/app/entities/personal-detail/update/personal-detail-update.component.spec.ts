import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonalDetailService } from '../service/personal-detail.service';
import { IPersonalDetail } from '../personal-detail.model';
import { PersonalDetailFormService } from './personal-detail-form.service';

import { PersonalDetailUpdateComponent } from './personal-detail-update.component';

describe('PersonalDetail Management Update Component', () => {
  let comp: PersonalDetailUpdateComponent;
  let fixture: ComponentFixture<PersonalDetailUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personalDetailFormService: PersonalDetailFormService;
  let personalDetailService: PersonalDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PersonalDetailUpdateComponent],
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
      .overrideTemplate(PersonalDetailUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonalDetailUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personalDetailFormService = TestBed.inject(PersonalDetailFormService);
    personalDetailService = TestBed.inject(PersonalDetailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personalDetail: IPersonalDetail = { id: 456 };

      activatedRoute.data = of({ personalDetail });
      comp.ngOnInit();

      expect(comp.personalDetail).toEqual(personalDetail);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonalDetail>>();
      const personalDetail = { id: 123 };
      jest.spyOn(personalDetailFormService, 'getPersonalDetail').mockReturnValue(personalDetail);
      jest.spyOn(personalDetailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personalDetail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personalDetail }));
      saveSubject.complete();

      // THEN
      expect(personalDetailFormService.getPersonalDetail).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personalDetailService.update).toHaveBeenCalledWith(expect.objectContaining(personalDetail));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonalDetail>>();
      const personalDetail = { id: 123 };
      jest.spyOn(personalDetailFormService, 'getPersonalDetail').mockReturnValue({ id: null });
      jest.spyOn(personalDetailService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personalDetail: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personalDetail }));
      saveSubject.complete();

      // THEN
      expect(personalDetailFormService.getPersonalDetail).toHaveBeenCalled();
      expect(personalDetailService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonalDetail>>();
      const personalDetail = { id: 123 };
      jest.spyOn(personalDetailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personalDetail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personalDetailService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
