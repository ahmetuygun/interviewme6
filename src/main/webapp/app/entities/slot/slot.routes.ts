import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SlotComponent } from './list/slot.component';
import { SlotDetailComponent } from './detail/slot-detail.component';
import { SlotUpdateComponent } from './update/slot-update.component';
import SlotResolve from './route/slot-routing-resolve.service';

const slotRoute: Routes = [
  {
    path: '',
    component: SlotComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SlotDetailComponent,
    resolve: {
      slot: SlotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SlotUpdateComponent,
    resolve: {
      slot: SlotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SlotUpdateComponent,
    resolve: {
      slot: SlotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default slotRoute;
