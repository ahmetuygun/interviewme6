import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { InterviewNoteComponent } from './list/interview-note.component';
import { InterviewNoteDetailComponent } from './detail/interview-note-detail.component';
import { InterviewNoteUpdateComponent } from './update/interview-note-update.component';
import InterviewNoteResolve from './route/interview-note-routing-resolve.service';

const interviewNoteRoute: Routes = [
  {
    path: '',
    component: InterviewNoteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterviewNoteDetailComponent,
    resolve: {
      interviewNote: InterviewNoteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterviewNoteUpdateComponent,
    resolve: {
      interviewNote: InterviewNoteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterviewNoteUpdateComponent,
    resolve: {
      interviewNote: InterviewNoteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default interviewNoteRoute;
