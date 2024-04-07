import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EducationEntryComponent } from './list/education-entry.component';
import { EducationEntryDetailComponent } from './detail/education-entry-detail.component';
import { EducationEntryUpdateComponent } from './update/education-entry-update.component';
import EducationEntryResolve from './route/education-entry-routing-resolve.service';

const educationEntryRoute: Routes = [
  {
    path: '',
    component: EducationEntryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationEntryDetailComponent,
    resolve: {
      educationEntry: EducationEntryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationEntryUpdateComponent,
    resolve: {
      educationEntry: EducationEntryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationEntryUpdateComponent,
    resolve: {
      educationEntry: EducationEntryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default educationEntryRoute;
