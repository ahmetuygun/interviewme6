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
import { IInterviewer } from '../interviewer.model';
import { InterviewerService } from '../service/interviewer.service';
import { InterviewerFormService } from './interviewer-form.service';

import { InterviewerUpdateComponent } from './interviewer-update.component';

describe('Interviewer Management Update Component', () => {
  let comp: InterviewerUpdateComponent;
  let fixture: ComponentFixture<InterviewerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let interviewerFormService: InterviewerFormService;
  let interviewerService: InterviewerService;
  let userService: UserService;
  let personalDetailService: PersonalDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), InterviewerUpdateComponent],
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
      .overrideTemplate(InterviewerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterviewerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interviewerFormService = TestBed.inject(InterviewerFormService);
    interviewerService = TestBed.inject(InterviewerService);
    userService = TestBed.inject(UserService);
    personalDetailService = TestBed.inject(PersonalDetailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const interviewer: IInterviewer = { id: 456 };
      const internalUser: IUser = { id: 21050 };
      interviewer.internalUser = internalUser;

      const userCollection: IUser[] = [{ id: 26748 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [internalUser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interviewer });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call personalDetail query and add missing value', () => {
      const interviewer: IInterviewer = { id: 456 };
      const personalDetail: IPersonalDetail = { id: 9607 };
      interviewer.personalDetail = personalDetail;

      const personalDetailCollection: IPersonalDetail[] = [{ id: 5925 }];
      jest.spyOn(personalDetailService, 'query').mockReturnValue(of(new HttpResponse({ body: personalDetailCollection })));
      const expectedCollection: IPersonalDetail[] = [personalDetail, ...personalDetailCollection];
      jest.spyOn(personalDetailService, 'addPersonalDetailToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interviewer });
      comp.ngOnInit();

      expect(personalDetailService.query).toHaveBeenCalled();
      expect(personalDetailService.addPersonalDetailToCollectionIfMissing).toHaveBeenCalledWith(personalDetailCollection, personalDetail);
      expect(comp.personalDetailsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const interviewer: IInterviewer = { id: 456 };
      const internalUser: IUser = { id: 18517 };
      interviewer.internalUser = internalUser;
      const personalDetail: IPersonalDetail = { id: 15500 };
      interviewer.personalDetail = personalDetail;

      activatedRoute.data = of({ interviewer });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(internalUser);
      expect(comp.personalDetailsCollection).toContain(personalDetail);
      expect(comp.interviewer).toEqual(interviewer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewer>>();
      const interviewer = { id: 123 };
      jest.spyOn(interviewerFormService, 'getInterviewer').mockReturnValue(interviewer);
      jest.spyOn(interviewerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewer }));
      saveSubject.complete();

      // THEN
      expect(interviewerFormService.getInterviewer).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interviewerService.update).toHaveBeenCalledWith(expect.objectContaining(interviewer));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewer>>();
      const interviewer = { id: 123 };
      jest.spyOn(interviewerFormService, 'getInterviewer').mockReturnValue({ id: null });
      jest.spyOn(interviewerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interviewer }));
      saveSubject.complete();

      // THEN
      expect(interviewerFormService.getInterviewer).toHaveBeenCalled();
      expect(interviewerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterviewer>>();
      const interviewer = { id: 123 };
      jest.spyOn(interviewerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interviewer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interviewerService.update).toHaveBeenCalled();
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
  });
});
