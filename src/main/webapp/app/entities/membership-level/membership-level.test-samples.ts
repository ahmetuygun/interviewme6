import { IMembershipLevel, NewMembershipLevel } from './membership-level.model';

export const sampleWithRequiredData: IMembershipLevel = {
  id: 13995,
};

export const sampleWithPartialData: IMembershipLevel = {
  id: 11482,
  description: 'cartilage',
  sessionAmount: 8674,
  price: 28598.5,
};

export const sampleWithFullData: IMembershipLevel = {
  id: 18416,
  name: 'calmly',
  description: 'streetcar',
  sessionAmount: 1663,
  price: 16071.2,
};

export const sampleWithNewData: NewMembershipLevel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
