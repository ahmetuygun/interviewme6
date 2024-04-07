import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'interviewer',
    data: { pageTitle: 'interviewme6App.interviewer.home.title' },
    loadChildren: () => import('./interviewer/interviewer.routes'),
  },
  {
    path: 'interviewee',
    data: { pageTitle: 'interviewme6App.interviewee.home.title' },
    loadChildren: () => import('./interviewee/interviewee.routes'),
  },
  {
    path: 'membership-level',
    data: { pageTitle: 'interviewme6App.membershipLevel.home.title' },
    loadChildren: () => import('./membership-level/membership-level.routes'),
  },
  {
    path: 'appointment',
    data: { pageTitle: "Appointment" },
    loadChildren: () => import('./appointment/appointment.routes'),
  },
  {
    path: 'interview-note',
    data: { pageTitle: 'interviewme6App.interviewNote.home.title' },
    loadChildren: () => import('./interview-note/interview-note.routes'),
  },
  {
    path: 'personal-detail',
    data: { pageTitle: 'interviewme6App.personalDetail.home.title' },
    loadChildren: () => import('./personal-detail/personal-detail.routes'),
  },
  {
    path: 'work-experience-entry',
    data: { pageTitle: 'interviewme6App.workExperienceEntry.home.title' },
    loadChildren: () => import('./work-experience-entry/work-experience-entry.routes'),
  },
  {
    path: 'education-entry',
    data: { pageTitle: 'interviewme6App.educationEntry.home.title' },
    loadChildren: () => import('./education-entry/education-entry.routes'),
  },
  {
    path: 'call',
    data: { pageTitle: 'interviewme6App.language.home.title' },
    loadChildren: () => import('./video-call/videoCall.routes'),
  },
  {
    path: 'slot',
    data: { pageTitle: 'interviewme6App.slot.home.title' },
    loadChildren: () => import('./slot/slot.routes'),
  },
  {
    path: 'interview-subject',
    data: { pageTitle: 'interviewme6App.interviewSubject.home.title' },
    loadChildren: () => import('./interview-subject/interview-subject.routes'),
  },

  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
