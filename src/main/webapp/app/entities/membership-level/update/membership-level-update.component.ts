import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMembershipLevel } from '../membership-level.model';
import { MembershipLevelService } from '../service/membership-level.service';
import { MembershipLevelFormService, MembershipLevelFormGroup } from './membership-level-form.service';

@Component({
  standalone: true,
  selector: 'jhi-membership-level-update',
  templateUrl: './membership-level-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MembershipLevelUpdateComponent implements OnInit {
  isSaving = false;
  membershipLevel: IMembershipLevel | null = null;

  editForm: MembershipLevelFormGroup = this.membershipLevelFormService.createMembershipLevelFormGroup();

  constructor(
    protected membershipLevelService: MembershipLevelService,
    protected membershipLevelFormService: MembershipLevelFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membershipLevel }) => {
      this.membershipLevel = membershipLevel;
      if (membershipLevel) {
        this.updateForm(membershipLevel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membershipLevel = this.membershipLevelFormService.getMembershipLevel(this.editForm);
    if (membershipLevel.id !== null) {
      this.subscribeToSaveResponse(this.membershipLevelService.update(membershipLevel));
    } else {
      this.subscribeToSaveResponse(this.membershipLevelService.create(membershipLevel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembershipLevel>>): void {
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

  protected updateForm(membershipLevel: IMembershipLevel): void {
    this.membershipLevel = membershipLevel;
    this.membershipLevelFormService.resetForm(this.editForm, membershipLevel);
  }
}
