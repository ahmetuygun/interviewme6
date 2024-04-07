import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import {AgoraAuthDetail} from "./AgoraAuthDetail";



@Injectable({ providedIn: 'root' })
export class VideoCallService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/video-call');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}


  getToken(channelName: string | undefined, userUid: number | undefined): Observable<AgoraAuthDetail> {
    const url = `${this.resourceUrl}/getToken?channelName=${channelName}&userUid=${userUid}`;
    return this.http.get<AgoraAuthDetail>(url);
  }


}
