import { IInterviewer, NewInterviewer } from './interviewer.model';

export const sampleWithRequiredData: IInterviewer = {
  id: 9528,
};

export const sampleWithPartialData: IInterviewer = {
  id: 21820,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithFullData: IInterviewer = {
  id: 2054,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithNewData: NewInterviewer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
