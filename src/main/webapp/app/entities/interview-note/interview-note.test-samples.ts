import { IInterviewNote, NewInterviewNote } from './interview-note.model';

export const sampleWithRequiredData: IInterviewNote = {
  id: 11024,
};

export const sampleWithPartialData: IInterviewNote = {
  id: 28879,
  feedback: 'panda truly',
};

export const sampleWithFullData: IInterviewNote = {
  id: 15880,
  noteText: 'regular mmm',
  rating: 20697,
  actionItems: 'the about mockingly',
  feedback: 'furthermore',
};

export const sampleWithNewData: NewInterviewNote = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
