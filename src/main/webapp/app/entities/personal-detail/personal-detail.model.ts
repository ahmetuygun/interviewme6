import dayjs from 'dayjs/esm';
import { IWorkExperienceEntry } from 'app/entities/work-experience-entry/work-experience-entry.model';
import { IEducationEntry } from 'app/entities/education-entry/education-entry.model';
import { IInterviewer } from 'app/entities/interviewer/interviewer.model';
import { IInterviewee } from 'app/entities/interviewee/interviewee.model';

export interface IPersonalDetail {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  rawName?: string | null;
  middle?: string | null;
  title?: string | null;
  prefix?: string | null;
  suffix?: string | null;
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
  selfSummary?: string | null;
  objective?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  placeOfBirth?: string | null;
  phones?: string | null;
  mails?: string | null;
  urls?: string | null;
  currentProfession?: string | null;
  gender?: string | null;
  nationality?: string | null;
  martialStatus?: string | null;
  currentSalary?: number | null;
  works?: IWorkExperienceEntry[] | null;
  educations?: IEducationEntry[] | null;
  interviewer?: IInterviewer | null;
  interviewee?: IInterviewee | null;
  skills?: string | null;
  languages?: string | null;
}

export type NewPersonalDetail = Omit<IPersonalDetail, 'id'> & { id: null };
