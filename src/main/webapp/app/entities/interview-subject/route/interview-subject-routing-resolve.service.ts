import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterviewSubject } from '../interview-subject.model';
import { InterviewSubjectService } from '../service/interview-subject.service';

export const interviewSubjectResolve = (route: ActivatedRouteSnapshot): Observable<null | IInterviewSubject> => {
  const id = route.params['id'];
  if (id) {
    return inject(InterviewSubjectService)
      .find(id)
      .pipe(
        mergeMap((interviewSubject: HttpResponse<IInterviewSubject>) => {
          if (interviewSubject.body) {
            return of(interviewSubject.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default interviewSubjectResolve;
