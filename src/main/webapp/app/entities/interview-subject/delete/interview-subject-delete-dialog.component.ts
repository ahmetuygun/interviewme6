import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInterviewSubject } from '../interview-subject.model';
import { InterviewSubjectService } from '../service/interview-subject.service';

@Component({
  standalone: true,
  templateUrl: './interview-subject-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InterviewSubjectDeleteDialogComponent {
  interviewSubject?: IInterviewSubject;

  constructor(
    protected interviewSubjectService: InterviewSubjectService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interviewSubjectService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
