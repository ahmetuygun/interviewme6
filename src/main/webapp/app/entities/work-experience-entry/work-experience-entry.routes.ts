import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { WorkExperienceEntryComponent } from './list/work-experience-entry.component';
import { WorkExperienceEntryDetailComponent } from './detail/work-experience-entry-detail.component';
import { WorkExperienceEntryUpdateComponent } from './update/work-experience-entry-update.component';
import WorkExperienceEntryResolve from './route/work-experience-entry-routing-resolve.service';

const workExperienceEntryRoute: Routes = [
  {
    path: '',
    component: WorkExperienceEntryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkExperienceEntryDetailComponent,
    resolve: {
      workExperienceEntry: WorkExperienceEntryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkExperienceEntryUpdateComponent,
    resolve: {
      workExperienceEntry: WorkExperienceEntryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkExperienceEntryUpdateComponent,
    resolve: {
      workExperienceEntry: WorkExperienceEntryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default workExperienceEntryRoute;
