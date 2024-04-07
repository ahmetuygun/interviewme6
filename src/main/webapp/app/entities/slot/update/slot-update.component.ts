import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { InterviewerService } from 'app/entities/interviewer/service/interviewer.service';
import {ISlot, ISlotX} from '../slot.model';
import { SlotService } from '../service/slot.service';
import { SlotFormService, SlotFormGroup } from './slot-form.service';
import {NgbCalendar, NgbDate, NgbDatepicker, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {AccountService} from "../../../core/auth/account.service";
import {TimeSlot} from "./TimeSlot";



@Component({
  standalone: true,
  selector: 'jhi-slot-update',
  templateUrl: './slot-update.component.html',
    imports: [SharedModule, FormsModule, ReactiveFormsModule, NgbDatepicker],
})
export class SlotUpdateComponent implements OnInit {
  constructor(
    protected slotService: SlotService,
    protected slotFormService: SlotFormService,
    protected interviewerService: InterviewerService,
    protected activatedRoute: ActivatedRoute,
    private calendar: NgbCalendar,
    private accountService: AccountService,

  ) {
    this.minDate = this.calendar.getToday();
    this.selectedDate = this.calendar.getToday();

    // Calculate max date (next 30 days)
    const next30Days = this.calendar.getNext(this.calendar.getToday(), 'd', 30);
    this.maxDate = { year: next30Days.year, month: next30Days.month, day: next30Days.day };
  }


  isSaving = false;
  slot: ISlot | null = null;

  interviewer: IInterviewer | null = null;


  interviewersSharedCollection: IInterviewer[] = [];

  editForm: SlotFormGroup = this.slotFormService.createSlotFormGroup();

   timeSlots : string[]  = [
    '08:00', '09:00', '10:00', '11:00',
    '12:00', '13:00', '14:00', '15:00',
    '16:00', '17:00', '18:00', '19:00',
    '20:00', '21:00', '22:00', '23:00', '24:00'
  ];

  selectedTimeSlots: TimeSlot[] = [];

  minDate: NgbDate;
  maxDate: { month: number; year: number; day: number };

  selectedDate: NgbDateStruct | undefined;

  // Handle date selection
  onDateSelection(date: NgbDateStruct) {
    // Reset available slots
    this.selectedDate = date;
  }
  selectSlot(slot: string) {
    if (!this.selectedDate) {
      return; // Return if no date is selected
    }

    // Find if the selected date exists in selectedTimeSlots
    const selectedDateIndex = this.selectedTimeSlots.findIndex(
      (item) =>
        item.date.year === this.selectedDate?.year &&
        item.date.month === this.selectedDate?.month &&
        item.date.day === this.selectedDate?.day
    );

    // If the selected date does not exist in selectedTimeSlots, add it
    if (selectedDateIndex === -1) {
      // Create a new entry for the selected date
      const newEntry = {
        date: {
          year: this.selectedDate.year,
          month: this.selectedDate.month,
          day: this.selectedDate.day
        },
        availableHours: [slot], // Add the selected slot to available hours
        enabled: true // Example: Set enabled to true
      };

      // Push the new entry to selectedTimeSlots
      this.selectedTimeSlots.push(newEntry);
    } else {
      // If the selected date exists in selectedTimeSlots
      // Check if the slot exists in the availableHours array of that date
      if (this.selectedTimeSlots[selectedDateIndex].availableHours.includes(slot)) {
        console.log(`Slot ${slot} is already selected for date ${this.selectedDate}`);

        const index = this.selectedTimeSlots[selectedDateIndex].availableHours.indexOf(slot);
        if (index !== -1) {
          // Slot is selected, perform any additional logic here
          console.log(`Slot ${slot} is already selected for date ${this.selectedDate}`);
          this.selectedTimeSlots[selectedDateIndex].availableHours.splice(index, 1);
        }
        // Slot is selected, perform any additional logic here

      } else {
        // Slot is not selected, add it to the availableHours array
        this.selectedTimeSlots[selectedDateIndex].availableHours.push(slot);
        console.log(`Slot ${slot} is added for date ${this.selectedDate}`);
      }
    }
  }



  compareInterviewer = (o1: IInterviewer | null, o2: IInterviewer | null): boolean => this.interviewerService.compareInterviewer(o1, o2);

  ngOnInit(): void {
    this.loadInterviewerData();
    this.activatedRoute.data.subscribe(({ slot }) => {
      this.slot = slot;
      if (slot) {
        this.updateForm(slot);
      }
      this.loadRelationshipsOptions();
    });
  }

  loadInterviewerData(): void {
    this.accountService.identity().subscribe(account => {
      if (account?.id) {
        this.interviewerService.findByInternalUserId(account.id).subscribe(interviewer => {
          this.handleInterviewerData(interviewer);
        });
      }
    });
  }

  handleInterviewerData(interviewer: any): void {
    if (interviewer.body && interviewer.body.length > 0) {
      this.interviewer = interviewer.body[0];
      const interviewerId: string | undefined = this.interviewer?.id.toString();
      const queryObject: any = {
        "interviewerId.in": interviewerId
      };
      this.queryTimeSlots(queryObject);
    }
  }

  queryTimeSlots(queryObject: any): void {
    this.slotService.query(queryObject).subscribe(resp => {
       this.toTimeSlot(resp.body);
    });
  }

  toTimeSlot(body: ISlot[] | null) {
    body?.forEach(item => {
      function createSlot(item: ISlot) {
        const slotX: TimeSlot = {
          date: {
            day: item.day ?? 0,
            year: item.year ?? 0,
            month: item.month ?? 0
          },
          availableHours: item.availableHours ?? [], // Provide an empty array if 'item.availableHours' is null or undefined
          enabled: true
        };
        return slotX; // Return the created TimeSlot object
      }

      this.selectedTimeSlots.push(createSlot(item))
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const slotX: ISlotX = {
      slots: this.selectedTimeSlots,
      interviewerId: this.interviewer?.id
    };
    this.subscribeToSaveResponse(this.slotService.createX(slotX));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISlot>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
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

  protected updateForm(slot: ISlot): void {
    this.slot = slot;
    this.slotFormService.resetForm(this.editForm, slot);

    this.interviewersSharedCollection = this.interviewerService.addInterviewerToCollectionIfMissing<IInterviewer>(
      this.interviewersSharedCollection,
      slot.interviewer,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.interviewerService
      .query()
      .pipe(map((res: HttpResponse<IInterviewer[]>) => res.body ?? []))
      .pipe(
        map((interviewers: IInterviewer[]) =>
          this.interviewerService.addInterviewerToCollectionIfMissing<IInterviewer>(interviewers, this.slot?.interviewer),
        ),
      )
      .subscribe((interviewers: IInterviewer[]) => (this.interviewersSharedCollection = interviewers));
  }

  isSlotSelected(slot: string): boolean {
    if (!this.selectedDate) {
      return false; // If no date is selected, slot is not selected
    }

    // Find if the selected date exists in selectedTimeSlots
    const selectedDateIndex = this.selectedTimeSlots.findIndex(
      (item) =>
        item.date.year === this.selectedDate?.year &&
        item.date.month === this.selectedDate?.month &&
        item.date.day === this.selectedDate?.day
    );

    // If the selected date exists in selectedTimeSlots
    if (selectedDateIndex !== -1) {
      // Check if the slot exists in the availableHours of the selected date
      return this.selectedTimeSlots[selectedDateIndex].availableHours.includes(slot);
    }

    return false; // If the selected date does not exist in selectedTimeSlots, slot is not selected
  }
}
