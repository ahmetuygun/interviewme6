import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IWorkExperienceEntry } from '../work-experience-entry.model';

@Component({
  standalone: true,
  selector: 'jhi-work-experience-entry-detail',
  templateUrl: './work-experience-entry-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class WorkExperienceEntryDetailComponent {
  @Input() workExperienceEntry: IWorkExperienceEntry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
