import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { IntervieweeComponent } from './list/interviewee.component';
import { IntervieweeDetailComponent } from './detail/interviewee-detail.component';
import { IntervieweeUpdateComponent } from './update/interviewee-update.component';
import IntervieweeResolve from './route/interviewee-routing-resolve.service';

const intervieweeRoute: Routes = [
  {
    path: '',
    component: IntervieweeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IntervieweeDetailComponent,
    resolve: {
      interviewee: IntervieweeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IntervieweeUpdateComponent,
    resolve: {
      interviewee: IntervieweeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IntervieweeUpdateComponent,
    resolve: {
      interviewee: IntervieweeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default intervieweeRoute;
