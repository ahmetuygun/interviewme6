import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { PersonalDetailService } from '../service/personal-detail.service';
import { IPersonalDetail } from '../personal-detail.model';
import { PersonalDetailFormService, PersonalDetailFormGroup } from './personal-detail-form.service';

@Component({
  standalone: true,
  selector: 'jhi-personal-detail-update',
  templateUrl: './personal-detail-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PersonalDetailUpdateComponent implements OnInit {
  isSaving = false;
  personalDetail: IPersonalDetail | null = null;

  editForm: PersonalDetailFormGroup = this.personalDetailFormService.createPersonalDetailFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected personalDetailService: PersonalDetailService,
    protected personalDetailFormService: PersonalDetailFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalDetail }) => {
      this.personalDetail = personalDetail;
      if (personalDetail) {
        this.updateForm(personalDetail);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('interviewme6App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personalDetail = this.personalDetailFormService.getPersonalDetail(this.editForm);
    if (personalDetail.id !== null) {
      this.subscribeToSaveResponse(this.personalDetailService.update(personalDetail));
    } else {
      this.subscribeToSaveResponse(this.personalDetailService.create(personalDetail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonalDetail>>): void {
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

  protected updateForm(personalDetail: IPersonalDetail): void {
    this.personalDetail = personalDetail;
    this.personalDetailFormService.resetForm(this.editForm, personalDetail);
  }
}
