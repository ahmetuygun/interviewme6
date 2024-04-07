import { Routes } from '@angular/router';

import { VideoCallDetailComponent } from './detail/video-call-detail.component';


const videoCallRoute: Routes = [
  {
    path: ':userId/:channel',
    component: VideoCallDetailComponent,
  },
];

export default videoCallRoute;
