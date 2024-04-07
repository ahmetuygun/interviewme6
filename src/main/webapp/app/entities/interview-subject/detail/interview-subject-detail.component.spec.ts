import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { InterviewSubjectDetailComponent } from './interview-subject-detail.component';

describe('InterviewSubject Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InterviewSubjectDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: InterviewSubjectDetailComponent,
              resolve: { interviewSubject: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(InterviewSubjectDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load interviewSubject on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', InterviewSubjectDetailComponent);

      // THEN
      expect(instance.interviewSubject).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
