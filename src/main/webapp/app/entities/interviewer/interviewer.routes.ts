import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { InterviewerComponent } from './list/interviewer.component';
import { InterviewerDetailComponent } from './detail/interviewer-detail.component';
import { InterviewerUpdateComponent } from './update/interviewer-update.component';
import InterviewerResolve from './route/interviewer-routing-resolve.service';

const interviewerRoute: Routes = [
  {
    path: '',
    component: InterviewerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterviewerDetailComponent,
    resolve: {
      interviewer: InterviewerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterviewerUpdateComponent,
    resolve: {
      interviewer: InterviewerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterviewerUpdateComponent,
    resolve: {
      interviewer: InterviewerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default interviewerRoute;
