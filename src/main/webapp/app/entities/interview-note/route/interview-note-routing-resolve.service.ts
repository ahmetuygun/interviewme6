import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterviewNote } from '../interview-note.model';
import { InterviewNoteService } from '../service/interview-note.service';

export const interviewNoteResolve = (route: ActivatedRouteSnapshot): Observable<null | IInterviewNote> => {
  const id = route.params['id'];
  if (id) {
    return inject(InterviewNoteService)
      .find(id)
      .pipe(
        mergeMap((interviewNote: HttpResponse<IInterviewNote>) => {
          if (interviewNote.body) {
            return of(interviewNote.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default interviewNoteResolve;
