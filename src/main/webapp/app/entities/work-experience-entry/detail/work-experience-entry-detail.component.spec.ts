import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { WorkExperienceEntryDetailComponent } from './work-experience-entry-detail.component';

describe('WorkExperienceEntry Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkExperienceEntryDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: WorkExperienceEntryDetailComponent,
              resolve: { workExperienceEntry: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(WorkExperienceEntryDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load workExperienceEntry on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', WorkExperienceEntryDetailComponent);

      // THEN
      expect(instance.workExperienceEntry).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
