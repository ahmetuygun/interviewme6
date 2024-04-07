import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { PersonalDetailService } from 'app/entities/personal-detail/service/personal-detail.service';
import { IEducationEntry } from '../education-entry.model';
import { EducationEntryService } from '../service/education-entry.service';
import { EducationEntryFormService, EducationEntryFormGroup } from './education-entry-form.service';

@Component({
  standalone: true,
  selector: 'jhi-education-entry-update',
  templateUrl: './education-entry-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EducationEntryUpdateComponent implements OnInit {
  isSaving = false;
  educationEntry: IEducationEntry | null = null;

  personalDetailsSharedCollection: IPersonalDetail[] = [];

  editForm: EducationEntryFormGroup = this.educationEntryFormService.createEducationEntryFormGroup();

  constructor(
    protected educationEntryService: EducationEntryService,
    protected educationEntryFormService: EducationEntryFormService,
    protected personalDetailService: PersonalDetailService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  comparePersonalDetail = (o1: IPersonalDetail | null, o2: IPersonalDetail | null): boolean =>
    this.personalDetailService.comparePersonalDetail(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationEntry }) => {
      this.educationEntry = educationEntry;
      if (educationEntry) {
        this.updateForm(educationEntry);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const educationEntry = this.educationEntryFormService.getEducationEntry(this.editForm);
    if (educationEntry.id !== null) {
      this.subscribeToSaveResponse(this.educationEntryService.update(educationEntry));
    } else {
      this.subscribeToSaveResponse(this.educationEntryService.create(educationEntry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducationEntry>>): void {
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

  protected updateForm(educationEntry: IEducationEntry): void {
    this.educationEntry = educationEntry;
    this.educationEntryFormService.resetForm(this.editForm, educationEntry);

    this.personalDetailsSharedCollection = this.personalDetailService.addPersonalDetailToCollectionIfMissing<IPersonalDetail>(
      this.personalDetailsSharedCollection,
      educationEntry.personalDetail,
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
            this.educationEntry?.personalDetail,
          ),
        ),
      )
      .subscribe((personalDetails: IPersonalDetail[]) => (this.personalDetailsSharedCollection = personalDetails));
  }
}
