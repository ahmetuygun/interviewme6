import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MembershipLevelService } from '../service/membership-level.service';
import { IMembershipLevel } from '../membership-level.model';
import { MembershipLevelFormService } from './membership-level-form.service';

import { MembershipLevelUpdateComponent } from './membership-level-update.component';

describe('MembershipLevel Management Update Component', () => {
  let comp: MembershipLevelUpdateComponent;
  let fixture: ComponentFixture<MembershipLevelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let membershipLevelFormService: MembershipLevelFormService;
  let membershipLevelService: MembershipLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MembershipLevelUpdateComponent],
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
      .overrideTemplate(MembershipLevelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembershipLevelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    membershipLevelFormService = TestBed.inject(MembershipLevelFormService);
    membershipLevelService = TestBed.inject(MembershipLevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const membershipLevel: IMembershipLevel = { id: 456 };

      activatedRoute.data = of({ membershipLevel });
      comp.ngOnInit();

      expect(comp.membershipLevel).toEqual(membershipLevel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembershipLevel>>();
      const membershipLevel = { id: 123 };
      jest.spyOn(membershipLevelFormService, 'getMembershipLevel').mockReturnValue(membershipLevel);
      jest.spyOn(membershipLevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membershipLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membershipLevel }));
      saveSubject.complete();

      // THEN
      expect(membershipLevelFormService.getMembershipLevel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(membershipLevelService.update).toHaveBeenCalledWith(expect.objectContaining(membershipLevel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembershipLevel>>();
      const membershipLevel = { id: 123 };
      jest.spyOn(membershipLevelFormService, 'getMembershipLevel').mockReturnValue({ id: null });
      jest.spyOn(membershipLevelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membershipLevel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membershipLevel }));
      saveSubject.complete();

      // THEN
      expect(membershipLevelFormService.getMembershipLevel).toHaveBeenCalled();
      expect(membershipLevelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembershipLevel>>();
      const membershipLevel = { id: 123 };
      jest.spyOn(membershipLevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membershipLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(membershipLevelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
