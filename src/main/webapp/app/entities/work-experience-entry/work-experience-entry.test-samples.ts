import dayjs from 'dayjs/esm';

import { IWorkExperienceEntry, NewWorkExperienceEntry } from './work-experience-entry.model';

export const sampleWithRequiredData: IWorkExperienceEntry = {
  id: 29267,
};

export const sampleWithPartialData: IWorkExperienceEntry = {
  id: 25320,
  title: 'only especially',
  endDate: dayjs('2024-01-27T09:58'),
  description: 'dreamily hm that',
  industry: 'when',
  postalCode: 'costly',
  country: "Cote d'Ivoire",
  rawInputLocation: 'by nearly',
  apartmentNumber: 'stagger loud so',
};

export const sampleWithFullData: IWorkExperienceEntry = {
  id: 9019,
  title: 'duckling',
  startDate: dayjs('2024-01-27T09:34'),
  endDate: dayjs('2024-01-27T02:55'),
  company: 'gingerbread',
  description: 'tut',
  industry: 'absent permafrost',
  formattedLocation: 'spotless round absentmindedly',
  postalCode: 'nor this',
  region: 'loftily pace',
  country: 'Ukraine',
  countryCode: 'KM',
  rawInputLocation: 'boo',
  street: 'Dicki Meadows',
  streetNumber: 'furthermore roughly',
  apartmentNumber: 'complicated serene',
  city: 'South Vincenzabury',
};

export const sampleWithNewData: NewWorkExperienceEntry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
