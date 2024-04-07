import { IAppointment, NewAppointment } from './appointment.model';

export const sampleWithRequiredData: IAppointment = {
  id: 6318,
};

export const sampleWithPartialData: IAppointment = {
  id: 14643,
  year: 4985,
  slot: 'concerning',
};

export const sampleWithFullData: IAppointment = {
  id: 9756,
  status: 'COMPLETED',
  year: 9896,
  month: 18344,
  day: 11321,
  slot: 'modulo pillory',
  appointmentUid: 'woot geez',
};

export const sampleWithNewData: NewAppointment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
