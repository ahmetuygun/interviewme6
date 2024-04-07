import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEducationEntry } from '../education-entry.model';
import { EducationEntryService } from '../service/education-entry.service';

@Component({
  standalone: true,
  templateUrl: './education-entry-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EducationEntryDeleteDialogComponent {
  educationEntry?: IEducationEntry;

  constructor(
    protected educationEntryService: EducationEntryService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.educationEntryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
