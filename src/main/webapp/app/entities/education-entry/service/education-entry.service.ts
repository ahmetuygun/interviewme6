import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEducationEntry, NewEducationEntry } from '../education-entry.model';

export type PartialUpdateEducationEntry = Partial<IEducationEntry> & Pick<IEducationEntry, 'id'>;

type RestOf<T extends IEducationEntry | NewEducationEntry> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

export type RestEducationEntry = RestOf<IEducationEntry>;

export type NewRestEducationEntry = RestOf<NewEducationEntry>;

export type PartialUpdateRestEducationEntry = RestOf<PartialUpdateEducationEntry>;

export type EntityResponseType = HttpResponse<IEducationEntry>;
export type EntityArrayResponseType = HttpResponse<IEducationEntry[]>;

@Injectable({ providedIn: 'root' })
export class EducationEntryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/education-entries');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(educationEntry: NewEducationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(educationEntry);
    return this.http
      .post<RestEducationEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(educationEntry: IEducationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(educationEntry);
    return this.http
      .put<RestEducationEntry>(`${this.resourceUrl}/${this.getEducationEntryIdentifier(educationEntry)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(educationEntry: PartialUpdateEducationEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(educationEntry);
    return this.http
      .patch<RestEducationEntry>(`${this.resourceUrl}/${this.getEducationEntryIdentifier(educationEntry)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEducationEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEducationEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEducationEntryIdentifier(educationEntry: Pick<IEducationEntry, 'id'>): number {
    return educationEntry.id;
  }

  compareEducationEntry(o1: Pick<IEducationEntry, 'id'> | null, o2: Pick<IEducationEntry, 'id'> | null): boolean {
    return o1 && o2 ? this.getEducationEntryIdentifier(o1) === this.getEducationEntryIdentifier(o2) : o1 === o2;
  }

  addEducationEntryToCollectionIfMissing<Type extends Pick<IEducationEntry, 'id'>>(
    educationEntryCollection: Type[],
    ...educationEntriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const educationEntries: Type[] = educationEntriesToCheck.filter(isPresent);
    if (educationEntries.length > 0) {
      const educationEntryCollectionIdentifiers = educationEntryCollection.map(
        educationEntryItem => this.getEducationEntryIdentifier(educationEntryItem)!,
      );
      const educationEntriesToAdd = educationEntries.filter(educationEntryItem => {
        const educationEntryIdentifier = this.getEducationEntryIdentifier(educationEntryItem);
        if (educationEntryCollectionIdentifiers.includes(educationEntryIdentifier)) {
          return false;
        }
        educationEntryCollectionIdentifiers.push(educationEntryIdentifier);
        return true;
      });
      return [...educationEntriesToAdd, ...educationEntryCollection];
    }
    return educationEntryCollection;
  }

  protected convertDateFromClient<T extends IEducationEntry | NewEducationEntry | PartialUpdateEducationEntry>(
    educationEntry: T,
  ): RestOf<T> {
    return {
      ...educationEntry,
      startDate: educationEntry.startDate?.format(DATE_FORMAT) ?? null,
      endDate: educationEntry.endDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEducationEntry: RestEducationEntry): IEducationEntry {
    return {
      ...restEducationEntry,
      startDate: restEducationEntry.startDate ? dayjs(restEducationEntry.startDate) : undefined,
      endDate: restEducationEntry.endDate ? dayjs(restEducationEntry.endDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEducationEntry>): HttpResponse<IEducationEntry> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEducationEntry[]>): HttpResponse<IEducationEntry[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
