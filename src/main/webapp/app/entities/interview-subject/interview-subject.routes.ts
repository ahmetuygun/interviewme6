import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { InterviewSubjectComponent } from './list/interview-subject.component';
import { InterviewSubjectDetailComponent } from './detail/interview-subject-detail.component';
import { InterviewSubjectUpdateComponent } from './update/interview-subject-update.component';
import InterviewSubjectResolve from './route/interview-subject-routing-resolve.service';

const interviewSubjectRoute: Routes = [
  {
    path: '',
    component: InterviewSubjectComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterviewSubjectDetailComponent,
    resolve: {
      interviewSubject: InterviewSubjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterviewSubjectUpdateComponent,
    resolve: {
      interviewSubject: InterviewSubjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterviewSubjectUpdateComponent,
    resolve: {
      interviewSubject: InterviewSubjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default interviewSubjectRoute;
