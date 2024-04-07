import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWorkExperienceEntry } from '../work-experience-entry.model';
import { WorkExperienceEntryService } from '../service/work-experience-entry.service';

@Component({
  standalone: true,
  templateUrl: './work-experience-entry-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WorkExperienceEntryDeleteDialogComponent {
  workExperienceEntry?: IWorkExperienceEntry;

  constructor(
    protected workExperienceEntryService: WorkExperienceEntryService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workExperienceEntryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
