import dayjs from 'dayjs/esm';
import { IPersonalDetail } from 'app/entities/personal-detail/personal-detail.model';

export interface IEducationEntry {
  id: number;
  title?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  establishment?: string | null;
  description?: string | null;
  gpa?: number | null;
  accreditation?: string | null;
  personalDetail?: IPersonalDetail | null;
}

export type NewEducationEntry = Omit<IEducationEntry, 'id'> & { id: null };
