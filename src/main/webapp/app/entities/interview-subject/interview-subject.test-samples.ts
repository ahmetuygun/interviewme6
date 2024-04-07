import { IInterviewSubject, NewInterviewSubject } from './interview-subject.model';

export const sampleWithRequiredData: IInterviewSubject = {
  id: 18510,
};

export const sampleWithPartialData: IInterviewSubject = {
  id: 29290,
};

export const sampleWithFullData: IInterviewSubject = {
  id: 20108,
  name: 'impolite application resign',
  parent: 'atomize psst jaded',
};

export const sampleWithNewData: NewInterviewSubject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
