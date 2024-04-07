import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAppointment, NewAppointment } from '../appointment.model';

export type PartialUpdateAppointment = Partial<IAppointment> & Pick<IAppointment, 'id'>;

export type EntityResponseType = HttpResponse<IAppointment>;
export type EntityArrayResponseType = HttpResponse<IAppointment[]>;

@Injectable({ providedIn: 'root' })
export class AppointmentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/appointments');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(appointment: IAppointment): Observable<EntityResponseType> {
    return this.http.post<IAppointment>(this.resourceUrl, appointment, { observe: 'response' });
  }

  update(appointment: IAppointment): Observable<EntityResponseType> {
    return this.http.put<IAppointment>(`${this.resourceUrl}/${this.getAppointmentIdentifier(appointment)}`, appointment, {
      observe: 'response',
    });
  }

  partialUpdate(appointment: PartialUpdateAppointment): Observable<EntityResponseType> {
    return this.http.patch<IAppointment>(`${this.resourceUrl}/${this.getAppointmentIdentifier(appointment)}`, appointment, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppointment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppointment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAppointmentIdentifier(appointment: Pick<IAppointment, 'id'>): number {
    return <number>appointment.id;
  }

  compareAppointment(o1: Pick<IAppointment, 'id'> | null, o2: Pick<IAppointment, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppointmentIdentifier(o1) === this.getAppointmentIdentifier(o2) : o1 === o2;
  }

  addAppointmentToCollectionIfMissing<Type extends Pick<IAppointment, 'id'>>(
    appointmentCollection: Type[],
    ...appointmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appointments: Type[] = appointmentsToCheck.filter(isPresent);
    if (appointments.length > 0) {
      const appointmentCollectionIdentifiers = appointmentCollection.map(
        appointmentItem => this.getAppointmentIdentifier(appointmentItem)!,
      );
      const appointmentsToAdd = appointments.filter(appointmentItem => {
        const appointmentIdentifier = this.getAppointmentIdentifier(appointmentItem);
        if (appointmentCollectionIdentifiers.includes(appointmentIdentifier)) {
          return false;
        }
        appointmentCollectionIdentifiers.push(appointmentIdentifier);
        return true;
      });
      return [...appointmentsToAdd, ...appointmentCollection];
    }
    return appointmentCollection;
  }
}
