import { IInterviewee } from 'app/entities/interviewee/interviewee.model';

export interface IMembershipLevel {
  id: number;
  name?: string | null;
  description?: string | null;
  sessionAmount?: number | null;
  price?: number | null;
  interviewees?: IInterviewee[] | null;
}

export type NewMembershipLevel = Omit<IMembershipLevel, 'id'> & { id: null };
