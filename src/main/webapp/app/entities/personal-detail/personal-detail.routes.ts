import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PersonalDetailComponent } from './list/personal-detail.component';
import { PersonalDetailDetailComponent } from './detail/personal-detail-detail.component';
import { PersonalDetailUpdateComponent } from './update/personal-detail-update.component';
import PersonalDetailResolve from './route/personal-detail-routing-resolve.service';

const personalDetailRoute: Routes = [
  {
    path: '',
    component: PersonalDetailComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonalDetailDetailComponent,
    resolve: {
      personalDetail: PersonalDetailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonalDetailUpdateComponent,
    resolve: {
      personalDetail: PersonalDetailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonalDetailUpdateComponent,
    resolve: {
      personalDetail: PersonalDetailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default personalDetailRoute;
