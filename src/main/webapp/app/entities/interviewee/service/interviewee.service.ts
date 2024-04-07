import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterviewee, NewInterviewee } from '../interviewee.model';

export type PartialUpdateInterviewee = Partial<IInterviewee> & Pick<IInterviewee, 'id'>;

export type EntityResponseType = HttpResponse<IInterviewee>;
export type EntityArrayResponseType = HttpResponse<IInterviewee[]>;

@Injectable({ providedIn: 'root' })
export class IntervieweeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interviewees');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(interviewee: NewInterviewee): Observable<EntityResponseType> {
    return this.http.post<IInterviewee>(this.resourceUrl, interviewee, { observe: 'response' });
  }

  update(interviewee: IInterviewee): Observable<EntityResponseType> {
    return this.http.put<IInterviewee>(`${this.resourceUrl}/${this.getIntervieweeIdentifier(interviewee)}`, interviewee, {
      observe: 'response',
    });
  }

  partialUpdate(interviewee: PartialUpdateInterviewee): Observable<EntityResponseType> {
    return this.http.patch<IInterviewee>(`${this.resourceUrl}/${this.getIntervieweeIdentifier(interviewee)}`, interviewee, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterviewee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterviewee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIntervieweeIdentifier(interviewee: Pick<IInterviewee, 'id'>): number {
    return interviewee.id;
  }

  compareInterviewee(o1: Pick<IInterviewee, 'id'> | null, o2: Pick<IInterviewee, 'id'> | null): boolean {
    return o1 && o2 ? this.getIntervieweeIdentifier(o1) === this.getIntervieweeIdentifier(o2) : o1 === o2;
  }

  addIntervieweeToCollectionIfMissing<Type extends Pick<IInterviewee, 'id'>>(
    intervieweeCollection: Type[],
    ...intervieweesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interviewees: Type[] = intervieweesToCheck.filter(isPresent);
    if (interviewees.length > 0) {
      const intervieweeCollectionIdentifiers = intervieweeCollection.map(
        intervieweeItem => this.getIntervieweeIdentifier(intervieweeItem)!,
      );
      const intervieweesToAdd = interviewees.filter(intervieweeItem => {
        const intervieweeIdentifier = this.getIntervieweeIdentifier(intervieweeItem);
        if (intervieweeCollectionIdentifiers.includes(intervieweeIdentifier)) {
          return false;
        }
        intervieweeCollectionIdentifiers.push(intervieweeIdentifier);
        return true;
      });
      return [...intervieweesToAdd, ...intervieweeCollection];
    }
    return intervieweeCollection;
  }

  findByInternalUserId(internalUserId: number) {
    return this.http.get<IInterviewee[]>(`${this.resourceUrl}?internalUserId.equals=${internalUserId}`, { observe: 'response' });

  }
}
