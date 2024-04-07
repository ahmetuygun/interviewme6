import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkExperienceEntry, NewWorkExperienceEntry } from '../work-experience-entry.model';

export type PartialUpdateWorkExperienceEntry = Partial<IWorkExperienceEntry> & Pick<IWorkExperienceEntry, 'id'>;

type RestOf<T extends IWorkExperienceEntry | NewWorkExperienceEntry> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

export type RestWorkExperienceEntry = RestOf<IWorkExperienceEntry>;

export type NewRestWorkExperienceEntry = RestOf<NewWorkExperienceEntry>;

export type PartialUpdateRestWorkExperienceEntry = RestOf<PartialUpdateWorkExperienceEntry>;

export type EntityResponseType = HttpResponse<IWorkExperienceEntry>;
export type EntityArrayResponseType = HttpResponse<IWorkExperienceEntry[]>;

@Injectable({ providedIn: 'root' })
export class WorkExperienceEntryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/work-experience-entries');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(workExperienceEntry: NewWorkExperienceEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workExperienceEntry);
    return this.http
      .post<RestWorkExperienceEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(workExperienceEntry: IWorkExperienceEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workExperienceEntry);
    return this.http
      .put<RestWorkExperienceEntry>(`${this.resourceUrl}/${this.getWorkExperienceEntryIdentifier(workExperienceEntry)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(workExperienceEntry: PartialUpdateWorkExperienceEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workExperienceEntry);
    return this.http
      .patch<RestWorkExperienceEntry>(`${this.resourceUrl}/${this.getWorkExperienceEntryIdentifier(workExperienceEntry)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestWorkExperienceEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestWorkExperienceEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkExperienceEntryIdentifier(workExperienceEntry: Pick<IWorkExperienceEntry, 'id'>): number {
    return workExperienceEntry.id;
  }

  compareWorkExperienceEntry(o1: Pick<IWorkExperienceEntry, 'id'> | null, o2: Pick<IWorkExperienceEntry, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkExperienceEntryIdentifier(o1) === this.getWorkExperienceEntryIdentifier(o2) : o1 === o2;
  }

  addWorkExperienceEntryToCollectionIfMissing<Type extends Pick<IWorkExperienceEntry, 'id'>>(
    workExperienceEntryCollection: Type[],
    ...workExperienceEntriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workExperienceEntries: Type[] = workExperienceEntriesToCheck.filter(isPresent);
    if (workExperienceEntries.length > 0) {
      const workExperienceEntryCollectionIdentifiers = workExperienceEntryCollection.map(
        workExperienceEntryItem => this.getWorkExperienceEntryIdentifier(workExperienceEntryItem)!,
      );
      const workExperienceEntriesToAdd = workExperienceEntries.filter(workExperienceEntryItem => {
        const workExperienceEntryIdentifier = this.getWorkExperienceEntryIdentifier(workExperienceEntryItem);
        if (workExperienceEntryCollectionIdentifiers.includes(workExperienceEntryIdentifier)) {
          return false;
        }
        workExperienceEntryCollectionIdentifiers.push(workExperienceEntryIdentifier);
        return true;
      });
      return [...workExperienceEntriesToAdd, ...workExperienceEntryCollection];
    }
    return workExperienceEntryCollection;
  }

  protected convertDateFromClient<T extends IWorkExperienceEntry | NewWorkExperienceEntry | PartialUpdateWorkExperienceEntry>(
    workExperienceEntry: T,
  ): RestOf<T> {
    return {
      ...workExperienceEntry,
      startDate: workExperienceEntry.startDate?.toJSON() ?? null,
      endDate: workExperienceEntry.endDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restWorkExperienceEntry: RestWorkExperienceEntry): IWorkExperienceEntry {
    return {
      ...restWorkExperienceEntry,
      startDate: restWorkExperienceEntry.startDate ? dayjs(restWorkExperienceEntry.startDate) : undefined,
      endDate: restWorkExperienceEntry.endDate ? dayjs(restWorkExperienceEntry.endDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestWorkExperienceEntry>): HttpResponse<IWorkExperienceEntry> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestWorkExperienceEntry[]>): HttpResponse<IWorkExperienceEntry[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
