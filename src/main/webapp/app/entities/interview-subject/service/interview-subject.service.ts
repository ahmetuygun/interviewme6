import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterviewSubject, NewInterviewSubject } from '../interview-subject.model';

export type PartialUpdateInterviewSubject = Partial<IInterviewSubject> & Pick<IInterviewSubject, 'id'>;

export type EntityResponseType = HttpResponse<IInterviewSubject>;
export type EntityArrayResponseType = HttpResponse<IInterviewSubject[]>;

@Injectable({ providedIn: 'root' })
export class InterviewSubjectService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interview-subjects');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(interviewSubject: NewInterviewSubject): Observable<EntityResponseType> {
    return this.http.post<IInterviewSubject>(this.resourceUrl, interviewSubject, { observe: 'response' });
  }

  update(interviewSubject: IInterviewSubject): Observable<EntityResponseType> {
    return this.http.put<IInterviewSubject>(
      `${this.resourceUrl}/${this.getInterviewSubjectIdentifier(interviewSubject)}`,
      interviewSubject,
      { observe: 'response' },
    );
  }

  partialUpdate(interviewSubject: PartialUpdateInterviewSubject): Observable<EntityResponseType> {
    return this.http.patch<IInterviewSubject>(
      `${this.resourceUrl}/${this.getInterviewSubjectIdentifier(interviewSubject)}`,
      interviewSubject,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterviewSubject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterviewSubject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInterviewSubjectIdentifier(interviewSubject: Pick<IInterviewSubject, 'id'>): number {
    return interviewSubject.id;
  }

  compareInterviewSubject(o1: Pick<IInterviewSubject, 'id'> | null, o2: Pick<IInterviewSubject, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterviewSubjectIdentifier(o1) === this.getInterviewSubjectIdentifier(o2) : o1 === o2;
  }

  addInterviewSubjectToCollectionIfMissing<Type extends Pick<IInterviewSubject, 'id'>>(
    interviewSubjectCollection: Type[],
    ...interviewSubjectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interviewSubjects: Type[] = interviewSubjectsToCheck.filter(isPresent);
    if (interviewSubjects.length > 0) {
      const interviewSubjectCollectionIdentifiers = interviewSubjectCollection.map(
        interviewSubjectItem => this.getInterviewSubjectIdentifier(interviewSubjectItem)!,
      );
      const interviewSubjectsToAdd = interviewSubjects.filter(interviewSubjectItem => {
        const interviewSubjectIdentifier = this.getInterviewSubjectIdentifier(interviewSubjectItem);
        if (interviewSubjectCollectionIdentifiers.includes(interviewSubjectIdentifier)) {
          return false;
        }
        interviewSubjectCollectionIdentifiers.push(interviewSubjectIdentifier);
        return true;
      });
      return [...interviewSubjectsToAdd, ...interviewSubjectCollection];
    }
    return interviewSubjectCollection;
  }
}
