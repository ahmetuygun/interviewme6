import dayjs from 'dayjs/esm';
import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import {TimeSlot} from "./update/TimeSlot";

export interface ISlot {
  id: number;
  isAvailable?: boolean | null;
  interviewer?: IInterviewer | null;
  availableHours?: String[] | null;
  day?: number;
  month?: number | null;
  year?: number | null;
}
export interface ISlotX {

  slots?: TimeSlot[] | null;
  interviewerId?: number | null;
}

export type NewSlot = Omit<ISlot, 'id'> & { id: null };
