import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInterviewee } from '../interviewee.model';
import { IntervieweeService } from '../service/interviewee.service';

@Component({
  standalone: true,
  templateUrl: './interviewee-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IntervieweeDeleteDialogComponent {
  interviewee?: IInterviewee;

  constructor(
    protected intervieweeService: IntervieweeService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.intervieweeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
