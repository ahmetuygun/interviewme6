import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { InterviewNoteDetailComponent } from './interview-note-detail.component';

describe('InterviewNote Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InterviewNoteDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: InterviewNoteDetailComponent,
              resolve: { interviewNote: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(InterviewNoteDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load interviewNote on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', InterviewNoteDetailComponent);

      // THEN
      expect(instance.interviewNote).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
