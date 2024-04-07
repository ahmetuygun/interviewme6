import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MembershipLevelDetailComponent } from './membership-level-detail.component';

describe('MembershipLevel Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MembershipLevelDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MembershipLevelDetailComponent,
              resolve: { membershipLevel: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MembershipLevelDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load membershipLevel on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MembershipLevelDetailComponent);

      // THEN
      expect(instance.membershipLevel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
