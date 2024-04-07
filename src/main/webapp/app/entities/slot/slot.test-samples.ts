import dayjs from 'dayjs/esm';

import { ISlot, NewSlot } from './slot.model';

export const sampleWithRequiredData: ISlot = {
  id: 30662,
};

export const sampleWithPartialData: ISlot = {
  id: 22711,
  slot: dayjs('2024-01-26T22:00'),
};

export const sampleWithFullData: ISlot = {
  id: 26721,
  slot: dayjs('2024-01-27T11:41'),
  isAvailable: false,
};

export const sampleWithNewData: NewSlot = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
