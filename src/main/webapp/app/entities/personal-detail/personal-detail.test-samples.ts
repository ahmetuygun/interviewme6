import dayjs from 'dayjs/esm';

import { IPersonalDetail, NewPersonalDetail } from './personal-detail.model';

export const sampleWithRequiredData: IPersonalDetail = {
  id: 22231,
};

export const sampleWithPartialData: IPersonalDetail = {
  id: 29520,
  cv: '../fake-data/blob/hipster.png',
  cvContentType: 'unknown',
  firstName: 'Cierra',
  rawName: 'homely',
  middle: 'for as',
  suffix: 'gasp about',
  formattedLocation: 'incidentally meh',
  postalCode: 'likewise bother because',
  region: 'knock hmph',
  rawInputLocation: 'afore painfully step-mother',
  street: 'Niko River',
  streetNumber: 'before protocol',
  city: 'Cranston',
  objective: 'vacantly aw',
  mails: 'whoever wherever credential',
  gender: 'although shuck sailor',
  nationality: 'heating',
  martialStatus: 'wig each',
};

export const sampleWithFullData: IPersonalDetail = {
  id: 22239,
  cv: '../fake-data/blob/hipster.png',
  cvContentType: 'unknown',
  firstName: 'Edwina',
  lastName: 'Abernathy',
  rawName: 'boohoo',
  middle: 'saffron overstate',
  title: 'freely',
  prefix: 'incidentally knavishly joyfully',
  suffix: 'except',
  formattedLocation: 'sharp eek debrief',
  postalCode: 'tomorrow phew',
  region: 'apud',
  country: 'Slovakia',
  countryCode: 'RE',
  rawInputLocation: 'compound though yahoo',
  street: 'Domingo Common',
  streetNumber: 'down vice revere',
  apartmentNumber: 'whereas efface',
  city: 'Reicherthaven',
  selfSummary: 'as',
  objective: 'uh-huh outstrip',
  dateOfBirth: dayjs('2024-01-27T20:07'),
  placeOfBirth: 'alongside seldom',
  phones: 'although swiftly upliftingly',
  mails: 'treasury',
  urls: 'portly',
  currentProfession: 'carefree blurt meanwhile',
  gender: 'bah',
  nationality: 'afore golden oh',
  martialStatus: 'meh',
  currentSalary: 3093.58,
};

export const sampleWithNewData: NewPersonalDetail = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
