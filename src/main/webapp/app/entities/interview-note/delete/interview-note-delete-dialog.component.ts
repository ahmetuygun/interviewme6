import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInterviewNote } from '../interview-note.model';
import { InterviewNoteService } from '../service/interview-note.service';

@Component({
  standalone: true,
  templateUrl: './interview-note-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InterviewNoteDeleteDialogComponent {
  interviewNote?: IInterviewNote;

  constructor(
    protected interviewNoteService: InterviewNoteService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interviewNoteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
