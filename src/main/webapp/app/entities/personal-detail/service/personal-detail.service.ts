import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonalDetail, NewPersonalDetail } from '../personal-detail.model';

export type PartialUpdatePersonalDetail = Partial<IPersonalDetail> & Pick<IPersonalDetail, 'id'>;

type RestOf<T extends IPersonalDetail | NewPersonalDetail> = Omit<T, 'dateOfBirth'> & {
  dateOfBirth?: string | null;
};

export type RestPersonalDetail = RestOf<IPersonalDetail>;

export type NewRestPersonalDetail = RestOf<NewPersonalDetail>;

export type PartialUpdateRestPersonalDetail = RestOf<PartialUpdatePersonalDetail>;

export type EntityResponseType = HttpResponse<IPersonalDetail>;
export type EntityArrayResponseType = HttpResponse<IPersonalDetail[]>;

@Injectable({ providedIn: 'root' })
export class PersonalDetailService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/personal-details');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(personalDetail: NewPersonalDetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalDetail);
    return this.http
      .post<RestPersonalDetail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(personalDetail: IPersonalDetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalDetail);
    return this.http
      .put<RestPersonalDetail>(`${this.resourceUrl}/${this.getPersonalDetailIdentifier(personalDetail)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(personalDetail: PartialUpdatePersonalDetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalDetail);
    return this.http
      .patch<RestPersonalDetail>(`${this.resourceUrl}/${this.getPersonalDetailIdentifier(personalDetail)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPersonalDetail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPersonalDetail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonalDetailIdentifier(personalDetail: Pick<IPersonalDetail, 'id'>): number {
    return personalDetail.id;
  }

  comparePersonalDetail(o1: Pick<IPersonalDetail, 'id'> | null, o2: Pick<IPersonalDetail, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonalDetailIdentifier(o1) === this.getPersonalDetailIdentifier(o2) : o1 === o2;
  }

  addPersonalDetailToCollectionIfMissing<Type extends Pick<IPersonalDetail, 'id'>>(
    personalDetailCollection: Type[],
    ...personalDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personalDetails: Type[] = personalDetailsToCheck.filter(isPresent);
    if (personalDetails.length > 0) {
      const personalDetailCollectionIdentifiers = personalDetailCollection.map(
        personalDetailItem => this.getPersonalDetailIdentifier(personalDetailItem)!,
      );
      const personalDetailsToAdd = personalDetails.filter(personalDetailItem => {
        const personalDetailIdentifier = this.getPersonalDetailIdentifier(personalDetailItem);
        if (personalDetailCollectionIdentifiers.includes(personalDetailIdentifier)) {
          return false;
        }
        personalDetailCollectionIdentifiers.push(personalDetailIdentifier);
        return true;
      });
      return [...personalDetailsToAdd, ...personalDetailCollection];
    }
    return personalDetailCollection;
  }

  protected convertDateFromClient<T extends IPersonalDetail | NewPersonalDetail | PartialUpdatePersonalDetail>(
    personalDetail: T,
  ): RestOf<T> {
    return {
      ...personalDetail,
      dateOfBirth: personalDetail.dateOfBirth?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPersonalDetail: RestPersonalDetail): IPersonalDetail {
    return {
      ...restPersonalDetail,
      dateOfBirth: restPersonalDetail.dateOfBirth ? dayjs(restPersonalDetail.dateOfBirth) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPersonalDetail>): HttpResponse<IPersonalDetail> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPersonalDetail[]>): HttpResponse<IPersonalDetail[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
