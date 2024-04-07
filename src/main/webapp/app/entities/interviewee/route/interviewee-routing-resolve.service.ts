import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterviewee } from '../interviewee.model';
import { IntervieweeService } from '../service/interviewee.service';

export const intervieweeResolve = (route: ActivatedRouteSnapshot): Observable<null | IInterviewee> => {
  const id = route.params['id'];
  if (id) {
    return inject(IntervieweeService)
      .find(id)
      .pipe(
        mergeMap((interviewee: HttpResponse<IInterviewee>) => {
          if (interviewee.body) {
            return of(interviewee.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default intervieweeResolve;
