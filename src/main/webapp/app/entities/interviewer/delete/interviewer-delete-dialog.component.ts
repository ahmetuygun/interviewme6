import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInterviewer } from '../interviewer.model';
import { InterviewerService } from '../service/interviewer.service';

@Component({
  standalone: true,
  templateUrl: './interviewer-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InterviewerDeleteDialogComponent {
  interviewer?: IInterviewer;

  constructor(
    protected interviewerService: InterviewerService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interviewerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
