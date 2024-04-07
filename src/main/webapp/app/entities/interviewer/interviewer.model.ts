import { IUser } from 'app/entities/user/user.model';
import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { IInterviewSubject } from 'app/entities/interview-subject/interview-subject.model';
import { ISlot } from 'app/entities/slot/slot.model';
import {IAppointment} from "../appointment/appointment.model";

export interface IInterviewer {
  id: number;
  photo?: string | null;
  photoContentType?: string | null;
  internalUser?: Pick<IUser, 'id'> | null;
  personalDetail?: IPersonalDetail | null;
  subjects?: IInterviewSubject[] | null;
  slots?: ISlot[] | null;
  appointments?: IAppointment[] | null;
  email?: string | null;
  cv?: string | null;
  cvContentType?: string | null;
}

export type NewInterviewer = Omit<IInterviewer, 'id'> & { id: null };
