import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { IInterviewee } from 'app/entities/interviewee/interviewee.model';
import { StatusType } from 'app/entities/enumerations/status-type.model';

export interface IAppointment {
  id: number | null;
  status?: keyof typeof StatusType | null;
  year?: number | null;
  month?: number | null;
  day?: number | null;
  slot?: string | null;
  appointmentUid?: string | null;
  interviewer?: IInterviewer | null;
  interviewee?: IInterviewee | null;
}

export type NewAppointment = Omit<IAppointment, 'id'> & { id: null };
