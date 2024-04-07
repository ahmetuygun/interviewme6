import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterviewer } from '../interviewer.model';
import { InterviewerService } from '../service/interviewer.service';

export const interviewerResolve = (route: ActivatedRouteSnapshot): Observable<null | IInterviewer> => {
  const id = route.params['id'];
  if (id) {
    return inject(InterviewerService)
      .find(id)
      .pipe(
        mergeMap((interviewer: HttpResponse<IInterviewer>) => {
          if (interviewer.body) {
            return of(interviewer.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default interviewerResolve;
