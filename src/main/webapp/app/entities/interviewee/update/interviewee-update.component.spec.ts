import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { PersonalDetailService } from 'app/entities/personal-detail/service/personal-detail.service';
import { IMembershipLevel } from 'app/entities/membership-level/membership-level.model';
import { MembershipLevelService } from 'app/entities/membership-level/service/membership-level.service';
import { IInterviewee } from '../interviewee.model';
import { IntervieweeService } from '../service/interviewee.service';
import { IntervieweeFormService } from './interviewee-form.service';

import { IntervieweeUpdateComponent } from './interviewee-update.component';

describe('Interviewee Management Update Component', () => {
  let comp: IntervieweeUpdateComponent;
  let fixture: ComponentFixture<IntervieweeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let intervieweeFormService: IntervieweeFormService;
  let intervieweeService: IntervieweeService;
  let userService: UserService;
  let personalDetailService: PersonalDetailService;
  let membershipLevelService: MembershipLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), IntervieweeUpdateComponent],
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
      .overrideTemplate(IntervieweeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IntervieweeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    intervieweeFormService = TestBed.inject(IntervieweeFormService);
    intervieweeService = TestBed.inject(IntervieweeService);
    userService = TestBed.inject(UserService);
    personalDetailService = TestBed.inject(PersonalDetailService);
    membershipLevelService = TestBed.inject(MembershipLevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const interviewee: IInterviewee = { id: 456 };
      const internalUser: IUser = { id: 9762 };
      interviewee.internalUser = internalUser;

      const userCollection: IUser[] = [{ id: 17387 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [internalUser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interviewee });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call personalDetail query and add missing value', () => {
      const interviewee: IInterviewee = { id: 456 };
      const personalDetail: IPersonalDetail = { id: 23101 };
      interviewee.personalDetail = personalDetail;

      const personalDetailCollection: IPersonalDetail[] = [{ id: 29914 }];
      jest.spyOn(personalDetailService, 'query').mockReturnValue(of(new HttpResponse({ body: personalDetailCollection })));
      const expectedCollection: IPersonalDetail[] = [personalDetail, ...personalDetailCollection];
      jest.spyOn(personalDetailService, 'addPersonalDetailToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interviewee });
      comp.ngOnInit();

      expect(personalDetailService.query).toHaveBeenCalled();
      expect(personalDetailService.addPersonalDetailToCollectionIfMissing).toHaveBeenCalledWith(personalDetailCollection, personalDetail);
      expect(comp.personalDetailsCollection).toEqual(expectedCollection);
    });

    it('Should call MembershipLevel query and add missing value', () => {
      const interviewee: IInterviewee = { id: 456 };
      const membership: IMembershipLevel = { id: 26877 };
      interviewee.membership = membership;

      const membershipLevelCollection: IMembershipLevel[] = [{ id: 15910 }];
      jest.spyOn(membershipLevelService, 'query').mockReturnValue(of(new HttpResponse({ body: membershipLevelCollection })));
      const additionalMembershipLevels = [membership];
      const expectedCollection: IMembershipLevel[] = [...additionalMembershipLevels, ...membershipLevelCollection];
      jest.spyOn(membershipLevelService, 'addMembershipLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interviewee });
      comp.ngOnInit();

      expect(membershipLevelService.query).toHaveBeenCalled();
      expect(membershipLevelService.addMembershipLevelToCollectionIfMissing).toHaveBeenCalledWith(
        membershipLevelCollection,
        ...additionalMembershipLevels.map(expect.objectContaining),
      );
      expect(comp.membershipLevelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const interviewee: IInterviewee = { id: 456 };
      const internalUser: IUser = { id: 29510 };
      interviewee.internalUser = internalUser;
      const personalDetail: IPersonalDetail = { id: 1692 };
      interviewee.personalDetail = personalDetail;
      const membership: IMembershipLevel = { id: 30285 };
      interviewee.membership = membership;

      activatedRoute.data = of({ interviewee });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(internalUser);
      expect(comp.personalDetailsCollection).toContain(personalDetail);
      expect(comp.membershipLevelsSharedCollection).toContain(membership);
      expect(comp.interviewee).toEqual(interviewee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewee>>();
      const interviewee = { id: 123 };
      jest.spyOn(intervieweeFormService, 'getInterviewee').mockReturnValue(interviewee);
      jest.spyOn(intervieweeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewee }));
      saveSubject.complete();

      // THEN
      expect(intervieweeFormService.getInterviewee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(intervieweeService.update).toHaveBeenCalledWith(expect.objectContaining(interviewee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewee>>();
      const interviewee = { id: 123 };
      jest.spyOn(intervieweeFormService, 'getInterviewee').mockReturnValue({ id: null });
      jest.spyOn(intervieweeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewee }));
      saveSubject.complete();

      // THEN
      expect(intervieweeFormService.getInterviewee).toHaveBeenCalled();
      expect(intervieweeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewee>>();
      const interviewee = { id: 123 };
      jest.spyOn(intervieweeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(intervieweeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePersonalDetail', () => {
      it('Should forward to personalDetailService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personalDetailService, 'comparePersonalDetail');
        comp.comparePersonalDetail(entity, entity2);
        expect(personalDetailService.comparePersonalDetail).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMembershipLevel', () => {
      it('Should forward to membershipLevelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(membershipLevelService, 'compareMembershipLevel');
        comp.compareMembershipLevel(entity, entity2);
        expect(membershipLevelService.compareMembershipLevel).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
