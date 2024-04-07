import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { InterviewerService } from 'app/entities/interviewer/service/interviewer.service';
import { IInterviewee } from 'app/entities/interviewee/interviewee.model';
import { IntervieweeService } from 'app/entities/interviewee/service/interviewee.service';
import { StatusType } from 'app/entities/enumerations/status-type.model';
import { AppointmentService } from '../service/appointment.service';
import { IAppointment } from '../appointment.model';
import { AppointmentFormService, AppointmentFormGroup } from './appointment-form.service';
import {AlertErrorComponent} from "../../../shared/alert/alert-error.component";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {NgbCalendar, NgbDate, NgbDatepicker, NgbDateStruct, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {ISlot} from "../../slot/slot.model";
import {SlotService} from "../../slot/service/slot.service";
import {AccountService} from "../../../core/auth/account.service";
import {PersonalDetailService} from "../../personal-detail/service/personal-detail.service";

@Component({
  standalone: true,
  selector: 'jhi-appointment-update',
  templateUrl: './appointment-update.component.html',
    imports: [SharedModule, FormsModule, ReactiveFormsModule, AlertErrorComponent, FaIconComponent, NgbDatepicker],
})
export class AppointmentUpdateComponent implements OnInit {
  isSaving = false;
  appointment: IAppointment | null = null;
  statusTypeValues = Object.keys(StatusType);


  editForm: AppointmentFormGroup = this.appointmentFormService.createAppointmentFormGroup();


  @Input() interviewer: IInterviewer | null = null;
  interviewee: IInterviewee | null = null;


  interviewersSharedCollection: IInterviewer[] = [];
  intervieweesSharedCollection: IInterviewee[] = [];

  dateList: ISlot[] = [
  ];

  model: NgbDateStruct | undefined;


  date: { year: number; month: number; } | undefined;

  availableSlots: String[] | null | undefined = [];

  timeSlots : string[]  = [
    '08:00', '09:00', '10:00', '11:00',
    '12:00', '13:00', '14:00', '15:00',
    '16:00', '17:00', '18:00', '19:00',
    '20:00', '21:00', '22:00', '23:00', '24:00'
  ];

  selectedSlot: string =  ""// Declare the selectedSlot variable


  minDate: NgbDate ;
  maxDate: { month: number; year: number; day: number };
  selectedDateX: NgbDateStruct | undefined;
  modalRef: NgbModalRef | undefined;


  appointmentDetail : IAppointment | undefined;


  constructor(
    protected appointmentService: AppointmentService,
    protected appointmentFormService: AppointmentFormService,
    protected interviewerService: InterviewerService,
    protected intervieweeService: IntervieweeService,
    protected activatedRoute: ActivatedRoute,
    private calendar: NgbCalendar,
    protected slotService: SlotService,
    private accountService: AccountService,
    private modalService: NgbModal,
    private router: Router,
  ) {

    this.minDate = this.calendar.getToday();
    // Calculate max date (next 30 days)
    const next30Days = this.calendar.getNext(this.calendar.getToday(), 'd', 30);
    this.maxDate = { year: next30Days.year, month: next30Days.month, day: next30Days.day };

  }

  compareInterviewer = (o1: IInterviewer | null, o2: IInterviewer | null): boolean => this.interviewerService.compareInterviewer(o1, o2);

  compareInterviewee = (o1: IInterviewee | null, o2: IInterviewee | null): boolean => this.intervieweeService.compareInterviewee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appointment }) => {
      this.appointment = appointment;
      if (appointment) {
        this.updateForm(appointment);
      }

      this.loadRelationshipsOptions();
    });

    const queryObject: any = {
      "interviewerId.in": this.interviewer?.id
    };
    this.queryTimeSlots(queryObject);
    this.setInterviewee();
  }

  public setInterviewee(): void  {
    this.accountService.identity().subscribe(account => {
      if (account?.id && account?.type == 'INTERVIEWEE') {
        this.intervieweeService.findByInternalUserId(account.id).subscribe(interviewee => {
          if( interviewee.body && interviewee.body[0].id){
            this.interviewee = interviewee.body[0];
          }
        });
      }
    });
  }

  queryTimeSlots(queryObject: any): void {
    this.slotService.query(queryObject).subscribe(resp => {
      resp.body?.forEach(item => {
        this.dateList.push(item)
      });
      this.onDateSelection( this.getToday());
    });
  }

  getToday(): NgbDateStruct {
    const today = new Date();
    return {
      year: today.getFullYear(),
      month: today.getMonth() + 1, // Note: Months are zero-based
      day: today.getDate()
    };
  }


  onDateSelection(date: NgbDateStruct) {
    // Reset available slots
    this.availableSlots = [];

    // Find selected date in dateList
    const selectedDate = this.dateList.find(
      item =>
        item.year === date.year &&
        item.month === date.month &&
        item.day === date.day
    );

    // If the selected date is found and enabled, set available slots
    if (selectedDate ) {
      this.availableSlots = selectedDate.availableHours;
    }
    this.selectedSlot = '';
    this.selectedDateX = date;
  }


  previousState(): void {
    window.history.back();
  }

  save(content: TemplateRef<any>): void {

    const appointment: IAppointment = {
      id: null,
      interviewee:  this.interviewee,
      interviewer: this.interviewer,
      year: this.selectedDateX?.year, // Set the year
      month : this.selectedDateX?.month,   // Set the month (January is 1, February is 2, ..., December is 12)
      day: this.selectedDateX?.day,    // Set the day
      slot : this.selectedSlot,
      status: StatusType.PENDING
    };

    this.isSaving = true;


    if (appointment.id !== null) {
      this.subscribeToSaveResponse(this.appointmentService.update(appointment));
    } else {
      this.subscribeToSaveResponse(this.appointmentService.create(appointment));
    }

    this.modalRef?.close(content);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppointment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.router.navigate(['/appointment', 'interviewee', this.interviewee?.id]);
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(appointment: IAppointment): void {
    this.appointment = appointment;
    this.appointmentFormService.resetForm(this.editForm, appointment);

    this.interviewersSharedCollection = this.interviewerService.addInterviewerToCollectionIfMissing<IInterviewer>(
      this.interviewersSharedCollection,
      appointment.interviewer,
    );
    this.intervieweesSharedCollection = this.intervieweeService.addIntervieweeToCollectionIfMissing<IInterviewee>(
      this.intervieweesSharedCollection,
      appointment.interviewee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.interviewerService
      .query()
      .pipe(map((res: HttpResponse<IInterviewer[]>) => res.body ?? []))
      .pipe(
        map((interviewers: IInterviewer[]) =>
          this.interviewerService.addInterviewerToCollectionIfMissing<IInterviewer>(interviewers, this.appointment?.interviewer),
        ),
      )
      .subscribe((interviewers: IInterviewer[]) => (this.interviewersSharedCollection = interviewers));

    this.intervieweeService
      .query()
      .pipe(map((res: HttpResponse<IInterviewee[]>) => res.body ?? []))
      .pipe(
        map((interviewees: IInterviewee[]) =>
          this.intervieweeService.addIntervieweeToCollectionIfMissing<IInterviewee>(interviewees, this.appointment?.interviewee),
        ),
      )
      .subscribe((interviewees: IInterviewee[]) => (this.intervieweesSharedCollection = interviewees));
  }


  async openVerticallyCentered($event: Event, content: TemplateRef<any>) {
    $event.preventDefault();

    if (!this.interviewer || !this.interviewee || !this.interviewer.personalDetail || !this.interviewee.personalDetail) {
      return; // Exit early if any required data is missing
    }

    try {

      this.appointmentDetail  = {
        id: null,
        interviewee: this.interviewee,
        interviewer : this.interviewer,
        year: this.selectedDateX?.year, // Set the year
        month : this.selectedDateX?.month,   // Set the month (January is 1, February is 2, ..., December is 12)
        day: this.selectedDateX?.day,    // Set the day
        slot : this.selectedSlot
      };;

      this.modalRef = this.modalService.open(content, { centered: true, animation:true, windowClass:"custom-modal" });
    } catch (error) {
      console.error('Error occurred while fetching personal details:', error);
      // Handle error (e.g., show error message to the user)
    }
  }

  isEnabledSlot(slot: string): boolean {
    return (this.availableSlots && this.availableSlots.includes(slot)) ? true : false;
  }


  getOneHourLater(inputTime: string | null | undefined): string {
    if (inputTime === null || inputTime === undefined) {
      return ''; // Return an empty string or handle null case as appropriate
    }
    // Split the input time string to hours and minutes
    const [hours, minutes] = inputTime.split(':').map(Number);

    // Calculate one hour later
    let newHours = hours + 1;
    let newMinutes = minutes;
    if (newHours >= 24) {
      newHours -= 24;
    }

    // Format the result
    const newTimeString = `${this.padZero(newHours)}:${this.padZero(newMinutes)}`;
    return newTimeString;
  }

  private padZero(num: number): string {
    return num < 10 ? `0${num}` : `${num}`;
  }

  formatDate(appointment?: IAppointment | undefined): string {
    if (!appointment || appointment.year === undefined || appointment.month === undefined || appointment.day === undefined) {
      return 'Invalid date';
    }

    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    if(appointment?.month){
      const month = months[appointment.month - 1]; // Adjust month index to match array index
      return `${month} ${appointment.day}, ${appointment.year}`;
    }
    return '';
  }

  selectSlot(slot: string) {
    this.selectedSlot = slot
    // Handle booking appointment logic here...
  }

}
