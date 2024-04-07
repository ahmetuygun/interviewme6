import { IInterviewee, NewInterviewee } from './interviewee.model';

export const sampleWithRequiredData: IInterviewee = {
  id: 20705,
};

export const sampleWithPartialData: IInterviewee = {
  id: 7610,
};

export const sampleWithFullData: IInterviewee = {
  id: 21449,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithNewData: NewInterviewee = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
