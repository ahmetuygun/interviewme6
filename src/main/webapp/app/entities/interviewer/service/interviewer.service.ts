import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterviewer, NewInterviewer } from '../interviewer.model';

export type PartialUpdateInterviewer = Partial<IInterviewer> & Pick<IInterviewer, 'id'>;

export type EntityResponseType = HttpResponse<IInterviewer>;
export type EntityArrayResponseType = HttpResponse<IInterviewer[]>;

@Injectable({ providedIn: 'root' })
export class InterviewerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interviewers');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(interviewer: NewInterviewer): Observable<EntityResponseType> {
    return this.http.post<IInterviewer>(this.resourceUrl, interviewer, { observe: 'response' });
  }

  update(interviewer: IInterviewer): Observable<EntityResponseType> {
    return this.http.put<IInterviewer>(`${this.resourceUrl}/${this.getInterviewerIdentifier(interviewer)}`, interviewer, {
      observe: 'response',
    });
  }

  partialUpdate(interviewer: PartialUpdateInterviewer): Observable<EntityResponseType> {
    return this.http.patch<IInterviewer>(`${this.resourceUrl}/${this.getInterviewerIdentifier(interviewer)}`, interviewer, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterviewer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByInternalUserId(internalUserId: number): Observable<EntityArrayResponseType> {
    return this.http.get<IInterviewer[]>(`${this.resourceUrl}?internalUserId.equals=${internalUserId}`, { observe: 'response' });
  }


  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterviewer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInterviewerIdentifier(interviewer: Pick<IInterviewer, 'id'>): number {
    return interviewer.id;
  }

  compareInterviewer(o1: Pick<IInterviewer, 'id'> | null, o2: Pick<IInterviewer, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterviewerIdentifier(o1) === this.getInterviewerIdentifier(o2) : o1 === o2;
  }

  addInterviewerToCollectionIfMissing<Type extends Pick<IInterviewer, 'id'>>(
    interviewerCollection: Type[],
    ...interviewersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interviewers: Type[] = interviewersToCheck.filter(isPresent);
    if (interviewers.length > 0) {
      const interviewerCollectionIdentifiers = interviewerCollection.map(
        interviewerItem => this.getInterviewerIdentifier(interviewerItem)!,
      );
      const interviewersToAdd = interviewers.filter(interviewerItem => {
        const interviewerIdentifier = this.getInterviewerIdentifier(interviewerItem);
        if (interviewerCollectionIdentifiers.includes(interviewerIdentifier)) {
          return false;
        }
        interviewerCollectionIdentifiers.push(interviewerIdentifier);
        return true;
      });
      return [...interviewersToAdd, ...interviewerCollection];
    }
    return interviewerCollection;
  }
}
