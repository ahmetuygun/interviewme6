import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkExperienceEntry } from '../work-experience-entry.model';
import { WorkExperienceEntryService } from '../service/work-experience-entry.service';

export const workExperienceEntryResolve = (route: ActivatedRouteSnapshot): Observable<null | IWorkExperienceEntry> => {
  const id = route.params['id'];
  if (id) {
    return inject(WorkExperienceEntryService)
      .find(id)
      .pipe(
        mergeMap((workExperienceEntry: HttpResponse<IWorkExperienceEntry>) => {
          if (workExperienceEntry.body) {
            return of(workExperienceEntry.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default workExperienceEntryResolve;
