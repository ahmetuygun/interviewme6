import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonalDetail } from '../personal-detail.model';
import { PersonalDetailService } from '../service/personal-detail.service';

export const personalDetailResolve = (route: ActivatedRouteSnapshot): Observable<null | IPersonalDetail> => {
  const id = route.params['id'];
  if (id) {
    return inject(PersonalDetailService)
      .find(id)
      .pipe(
        mergeMap((personalDetail: HttpResponse<IPersonalDetail>) => {
          if (personalDetail.body) {
            return of(personalDetail.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default personalDetailResolve;
