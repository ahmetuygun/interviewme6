import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SlotDetailComponent } from './slot-detail.component';

describe('Slot Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SlotDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SlotDetailComponent,
              resolve: { slot: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SlotDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load slot on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SlotDetailComponent);

      // THEN
      expect(instance.slot).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
