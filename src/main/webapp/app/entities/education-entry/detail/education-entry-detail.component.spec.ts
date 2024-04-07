import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EducationEntryDetailComponent } from './education-entry-detail.component';

describe('EducationEntry Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EducationEntryDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EducationEntryDetailComponent,
              resolve: { educationEntry: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EducationEntryDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load educationEntry on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EducationEntryDetailComponent);

      // THEN
      expect(instance.educationEntry).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
