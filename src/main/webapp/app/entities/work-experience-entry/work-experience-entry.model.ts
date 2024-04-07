import dayjs from 'dayjs/esm';
import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';

export interface IWorkExperienceEntry {
  id: number;
  title?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  company?: string | null;
  description?: string | null;
  industry?: string | null;
  formattedLocation?: string | null;
  postalCode?: string | null;
  region?: string | null;
  country?: string | null;
  countryCode?: string | null;
  rawInputLocation?: string | null;
  street?: string | null;
  streetNumber?: string | null;
  apartmentNumber?: string | null;
  city?: string | null;
  personalDetail?: IPersonalDetail | null;
}

export type NewWorkExperienceEntry = Omit<IWorkExperienceEntry, 'id'> & { id: null };
