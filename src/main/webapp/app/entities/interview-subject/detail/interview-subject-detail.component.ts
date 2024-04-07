import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IInterviewSubject } from '../interview-subject.model';

@Component({
  standalone: true,
  selector: 'jhi-interview-subject-detail',
  templateUrl: './interview-subject-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class InterviewSubjectDetailComponent {
  @Input() interviewSubject: IInterviewSubject | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
