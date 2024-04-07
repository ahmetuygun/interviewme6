import dayjs from 'dayjs/esm';

import { IEducationEntry, NewEducationEntry } from './education-entry.model';

export const sampleWithRequiredData: IEducationEntry = {
  id: 7341,
};

export const sampleWithPartialData: IEducationEntry = {
  id: 13911,
  title: 'plummet ack going',
  endDate: dayjs('2024-01-26'),
  establishment: 'mockingly euphoric giant',
  description: 'or',
  gpa: 6990.8,
};

export const sampleWithFullData: IEducationEntry = {
  id: 21753,
  title: 'ha',
  startDate: dayjs('2024-01-27'),
  endDate: dayjs('2024-01-27'),
  establishment: 'far driving',
  description: 'quick acclaimed',
  gpa: 26204.01,
  accreditation: 'during',
};

export const sampleWithNewData: NewEducationEntry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
