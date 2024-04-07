import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";

export interface TimeSlot {
  date: {day:number, month:number, year:number }; // Assuming NgbDateStruct is used to represent dates in Angular
  availableHours: String[];
  enabled: boolean;
}
