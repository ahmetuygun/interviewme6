import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MembershipLevelComponent } from './list/membership-level.component';
import { MembershipLevelDetailComponent } from './detail/membership-level-detail.component';
import { MembershipLevelUpdateComponent } from './update/membership-level-update.component';
import MembershipLevelResolve from './route/membership-level-routing-resolve.service';

const membershipLevelRoute: Routes = [
  {
    path: '',
    component: MembershipLevelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MembershipLevelDetailComponent,
    resolve: {
      membershipLevel: MembershipLevelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MembershipLevelUpdateComponent,
    resolve: {
      membershipLevel: MembershipLevelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MembershipLevelUpdateComponent,
    resolve: {
      membershipLevel: MembershipLevelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default membershipLevelRoute;
