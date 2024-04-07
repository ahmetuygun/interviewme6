import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMembershipLevel, NewMembershipLevel } from '../membership-level.model';

export type PartialUpdateMembershipLevel = Partial<IMembershipLevel> & Pick<IMembershipLevel, 'id'>;

export type EntityResponseType = HttpResponse<IMembershipLevel>;
export type EntityArrayResponseType = HttpResponse<IMembershipLevel[]>;

@Injectable({ providedIn: 'root' })
export class MembershipLevelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/membership-levels');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(membershipLevel: NewMembershipLevel): Observable<EntityResponseType> {
    return this.http.post<IMembershipLevel>(this.resourceUrl, membershipLevel, { observe: 'response' });
  }

  update(membershipLevel: IMembershipLevel): Observable<EntityResponseType> {
    return this.http.put<IMembershipLevel>(`${this.resourceUrl}/${this.getMembershipLevelIdentifier(membershipLevel)}`, membershipLevel, {
      observe: 'response',
    });
  }

  partialUpdate(membershipLevel: PartialUpdateMembershipLevel): Observable<EntityResponseType> {
    return this.http.patch<IMembershipLevel>(`${this.resourceUrl}/${this.getMembershipLevelIdentifier(membershipLevel)}`, membershipLevel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMembershipLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMembershipLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMembershipLevelIdentifier(membershipLevel: Pick<IMembershipLevel, 'id'>): number {
    return membershipLevel.id;
  }

  compareMembershipLevel(o1: Pick<IMembershipLevel, 'id'> | null, o2: Pick<IMembershipLevel, 'id'> | null): boolean {
    return o1 && o2 ? this.getMembershipLevelIdentifier(o1) === this.getMembershipLevelIdentifier(o2) : o1 === o2;
  }

  addMembershipLevelToCollectionIfMissing<Type extends Pick<IMembershipLevel, 'id'>>(
    membershipLevelCollection: Type[],
    ...membershipLevelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const membershipLevels: Type[] = membershipLevelsToCheck.filter(isPresent);
    if (membershipLevels.length > 0) {
      const membershipLevelCollectionIdentifiers = membershipLevelCollection.map(
        membershipLevelItem => this.getMembershipLevelIdentifier(membershipLevelItem)!,
      );
      const membershipLevelsToAdd = membershipLevels.filter(membershipLevelItem => {
        const membershipLevelIdentifier = this.getMembershipLevelIdentifier(membershipLevelItem);
        if (membershipLevelCollectionIdentifiers.includes(membershipLevelIdentifier)) {
          return false;
        }
        membershipLevelCollectionIdentifiers.push(membershipLevelIdentifier);
        return true;
      });
      return [...membershipLevelsToAdd, ...membershipLevelCollection];
    }
    return membershipLevelCollection;
  }
}
