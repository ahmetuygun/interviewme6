import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterviewNote, NewInterviewNote } from '../interview-note.model';

export type PartialUpdateInterviewNote = Partial<IInterviewNote> & Pick<IInterviewNote, 'id'>;

export type EntityResponseType = HttpResponse<IInterviewNote>;
export type EntityArrayResponseType = HttpResponse<IInterviewNote[]>;

@Injectable({ providedIn: 'root' })
export class InterviewNoteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interview-notes');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(interviewNote: NewInterviewNote): Observable<EntityResponseType> {
    return this.http.post<IInterviewNote>(this.resourceUrl, interviewNote, { observe: 'response' });
  }

  update(interviewNote: IInterviewNote): Observable<EntityResponseType> {
    return this.http.put<IInterviewNote>(`${this.resourceUrl}/${this.getInterviewNoteIdentifier(interviewNote)}`, interviewNote, {
      observe: 'response',
    });
  }

  partialUpdate(interviewNote: PartialUpdateInterviewNote): Observable<EntityResponseType> {
    return this.http.patch<IInterviewNote>(`${this.resourceUrl}/${this.getInterviewNoteIdentifier(interviewNote)}`, interviewNote, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterviewNote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterviewNote[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInterviewNoteIdentifier(interviewNote: Pick<IInterviewNote, 'id'>): number {
    return interviewNote.id;
  }

  compareInterviewNote(o1: Pick<IInterviewNote, 'id'> | null, o2: Pick<IInterviewNote, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterviewNoteIdentifier(o1) === this.getInterviewNoteIdentifier(o2) : o1 === o2;
  }

  addInterviewNoteToCollectionIfMissing<Type extends Pick<IInterviewNote, 'id'>>(
    interviewNoteCollection: Type[],
    ...interviewNotesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interviewNotes: Type[] = interviewNotesToCheck.filter(isPresent);
    if (interviewNotes.length > 0) {
      const interviewNoteCollectionIdentifiers = interviewNoteCollection.map(
        interviewNoteItem => this.getInterviewNoteIdentifier(interviewNoteItem)!,
      );
      const interviewNotesToAdd = interviewNotes.filter(interviewNoteItem => {
        const interviewNoteIdentifier = this.getInterviewNoteIdentifier(interviewNoteItem);
        if (interviewNoteCollectionIdentifiers.includes(interviewNoteIdentifier)) {
          return false;
        }
        interviewNoteCollectionIdentifiers.push(interviewNoteIdentifier);
        return true;
      });
      return [...interviewNotesToAdd, ...interviewNoteCollection];
    }
    return interviewNoteCollection;
  }
}
