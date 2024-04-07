import { IUser } from 'app/entities/user/user.model';
import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';
import { IMembershipLevel } from 'app/entities/membership-level/membership-level.model';
import {IAppointment} from "../appointment/appointment.model";

export interface IInterviewee {
  id: number;
  photo?: string | null;
  photoContentType?: string | null;
  internalUser?: Pick<IUser, 'id'> | null;
  personalDetail?: IPersonalDetail | null;
  membership?: IMembershipLevel | null;
  appointments?: IAppointment[] | null;
  cv?: string | null;
  cvContentType?: string | null;
}

export type NewInterviewee = Omit<IInterviewee, 'id'> & { id: null };
