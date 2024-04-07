import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { PersonalDetailService } from 'app/entities/personal-detail/service/personal-detail.service';
import { IWorkExperienceEntry } from '../work-experience-entry.model';
import { WorkExperienceEntryService } from '../service/work-experience-entry.service';
import { WorkExperienceEntryFormService, WorkExperienceEntryFormGroup } from './work-experience-entry-form.service';

@Component({
  standalone: true,
  selector: 'jhi-work-experience-entry-update',
  templateUrl: './work-experience-entry-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkExperienceEntryUpdateComponent implements OnInit {
  isSaving = false;
  workExperienceEntry: IWorkExperienceEntry | null = null;

  personalDetailsSharedCollection: IPersonalDetail[] = [];

  editForm: WorkExperienceEntryFormGroup = this.workExperienceEntryFormService.createWorkExperienceEntryFormGroup();

  constructor(
    protected workExperienceEntryService: WorkExperienceEntryService,
    protected workExperienceEntryFormService: WorkExperienceEntryFormService,
    protected personalDetailService: PersonalDetailService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  comparePersonalDetail = (o1: IPersonalDetail | null, o2: IPersonalDetail | null): boolean =>
    this.personalDetailService.comparePersonalDetail(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workExperienceEntry }) => {
      this.workExperienceEntry = workExperienceEntry;
      if (workExperienceEntry) {
        this.updateForm(workExperienceEntry);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workExperienceEntry = this.workExperienceEntryFormService.getWorkExperienceEntry(this.editForm);
    if (workExperienceEntry.id !== null) {
      this.subscribeToSaveResponse(this.workExperienceEntryService.update(workExperienceEntry));
    } else {
      this.subscribeToSaveResponse(this.workExperienceEntryService.create(workExperienceEntry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkExperienceEntry>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(workExperienceEntry: IWorkExperienceEntry): void {
    this.workExperienceEntry = workExperienceEntry;
    this.workExperienceEntryFormService.resetForm(this.editForm, workExperienceEntry);

    this.personalDetailsSharedCollection = this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
      this.personalDetailsSharedCollection,
      workExperienceEntry.personalDetail,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personalDetailService
      .query()
      .pipe(map((res: HttpResponse<IPersonalDetail[]>) => res.body ?? []))
      .pipe(
        map((personalDetails: IPersonalDetail[]) =>
          this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
            personalDetails,
            this.workExperienceEntry?.personalDetail,
          ),
        ),
      )
      .subscribe((personalDetails: IPersonalDetail[]) => (this.personalDetailsSharedCollection = personalDetails));
  }
}
