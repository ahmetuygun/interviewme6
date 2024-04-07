export interface IInterviewNote {
  id: number;
  noteText?: string | null;
  rating?: number | null;
  actionItems?: string | null;
  feedback?: string | null;
}

export type NewInterviewNote = Omit<IInterviewNote, 'id'> & { id: null };
