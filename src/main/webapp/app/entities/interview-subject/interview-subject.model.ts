import { IInterviewer } from 'app/entities/interviewer/interviewer.model';

export interface IInterviewSubject {
  id: number;
  name?: string | null;
  parent?: string | null;
  interviewer?: IInterviewer | null;
}

export type NewInterviewSubject = Omit<IInterviewSubject, 'id'> & { id: null };
