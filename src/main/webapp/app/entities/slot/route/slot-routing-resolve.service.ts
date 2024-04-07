import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISlot } from '../slot.model';
import { SlotService } from '../service/slot.service';

export const slotResolve = (route: ActivatedRouteSnapshot): Observable<null | ISlot> => {
  const id = route.params['id'];
  if (id) {
    return inject(SlotService)
      .find(id)
      .pipe(
        mergeMap((slot: HttpResponse<ISlot>) => {
          if (slot.body) {
            return of(slot.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default slotResolve;
