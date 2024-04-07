import {AfterViewInit, Component, ElementRef, OnInit, signal, ViewChild} from '@angular/core';
import AgoraRTC, { IAgoraRTCClient, IAgoraRTCRemoteUser, ICameraVideoTrack, IMicrophoneAudioTrack, IRemoteAudioTrack, IRemoteVideoTrack } from 'agora-rtc-sdk-ng';
import SharedModule from "../../../shared/shared.module";
import {ActivatedRoute, RouterModule} from "@angular/router";
import {DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe} from "../../../shared/date";
import {NgIf} from "@angular/common";
import {
  faEnvelope,
  faMicrophone,
  faMicrophoneSlash, faPhoneSlash,
  faPlay,
  faVideo,
  faVideoSlash
} from "@fortawesome/free-solid-svg-icons";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {InterviewNoteUpdateComponent} from "../../interview-note/update/interview-note-update.component";
import {InterviewerService} from "../../interviewer/service/interviewer.service";
import {VideoCallService} from "../videoCall.service";
import {catchError, tap} from "rxjs/operators";
import {throwError} from "rxjs";


@Component({
  standalone: true,
  selector: 'jhi-video-call-detail',
  templateUrl: './video-call-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, NgIf, FaIconComponent, InterviewNoteUpdateComponent],
})
export class VideoCallDetailComponent implements OnInit, AfterViewInit {
  @ViewChild('remotePlayerContainer', { static: false }) remotePlayerContainer!: ElementRef;
  @ViewChild('localPlayerContainer', { static: false }) localPlayerContainer!: ElementRef;

  userId : number |undefined;
  channel : string | undefined;
  token : String | undefined;
  appId : String | undefined;


  agoraEngine: IAgoraRTCClient | undefined;
  channelParameters: {
    // A variable to hold a local audio track.
    localAudioTrack?: IMicrophoneAudioTrack,
    // A variable to hold a local video track.
    localVideoTrack?: ICameraVideoTrack,
    // A variable to hold a remote audio track.
    remoteAudioTrack?: IRemoteAudioTrack,
    // A variable to hold a remote video track.
    remoteVideoTrack?: IRemoteVideoTrack,
    // A variable to hold the remote user id.s
    remoteUser?: IAgoraRTCRemoteUser,
    localUid?: string
  } = {};

  constructor(protected activatedRoute: ActivatedRoute,
              private videoCallService : VideoCallService) {
  }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params => {

      if(params['userId'] ){
       this.userId = params['userId'];
      }

      if (params['channel'] ){
        this.channel = params['channel'];;
      }
      this.fetchToken(this.channel, this.userId)

    });

  }

  fetchToken(channelName: string | undefined, userUid: number | undefined): void {
    this.videoCallService.getToken(channelName, userUid)
      .pipe(
        tap(token => {
          // Handle the token
          this.token = token.token;
          this.appId = token.appId
        }),
        catchError(error => {
          // Handle errors
          console.error('Error fetching token:', error);
          return throwError(error);
        })
      )
      .subscribe();
  }

  ngAfterViewInit(): void {
    this.agoraEngine = AgoraRTC.createClient({ mode: 'rtc', codec: 'vp9' });
    this.subscribeToEvents()
  }

  async joinByInfo() {
    if(this.userId && this.channel){
      await this.join(this.userId, this.channel)
    }
  };

  async join(userId: number, channel: string) {
    if (!this.token || !this.userId || !this.channel || !this.appId){
      return;
    }

    this.channelParameters.localUid = userId.toString();
    await this.agoraEngine?.join(
      this.appId.toString(),
      this.channel,
      this.token.toString(),
      this.userId
    );
    this.channelParameters.localAudioTrack = await AgoraRTC.createMicrophoneAudioTrack();
    this.channelParameters.localVideoTrack = await AgoraRTC.createCameraVideoTrack();
    await this.agoraEngine?.publish([
      this.channelParameters.localAudioTrack,
      this.channelParameters.localVideoTrack,
    ]);
    // Play the local video track.
    this.channelParameters.localVideoTrack.play(this.localPlayerContainer.nativeElement);
  };



  subscribeToEvents() {
    // Event Listeners
    this.agoraEngine?.on("user-published", async (user, mediaType) => {
      await this.agoraEngine?.subscribe(user, mediaType);
      console.log("User published -", mediaType);
      this.handleVSDKEvents("user-published", user, mediaType);
    });

    // Listen for the "user-unpublished" event.
    this.agoraEngine?.on("user-unpublished", (user, mediaType) => {
      console.log("user unpublished-", mediaType, '- has video -', user.hasVideo, '- has audio-', user.hasAudio);
    });

    this.agoraEngine?.on("user-joined", (user: IAgoraRTCRemoteUser) => {
      const userId = user.uid;
      console.log("remote user joined", userId);

    });
    this.agoraEngine?.on("user-left", (user: IAgoraRTCRemoteUser) => {
      console.log("Remote user left", user.uid);
      if (this.channelParameters) {
        this.channelParameters.remoteUser = undefined;
      }
    })

  }

  muteOrUnmuteAudio() {
    if (this.channelParameters?.localAudioTrack?.muted) {
      this.channelParameters.localAudioTrack.setMuted(false);
    } else {
      this.channelParameters?.localAudioTrack?.setMuted(true);
    }
  }

  turnCameraOnOrOff() {
    if (this.channelParameters?.localVideoTrack?.enabled) {
      this.channelParameters.localVideoTrack.setEnabled(false);
    } else {
      this.channelParameters?.localVideoTrack?.setEnabled(true);
    }
  }


  handleVSDKEvents = (eventName: string, user: IAgoraRTCRemoteUser, mediaType: 'video' | 'audio' | 'datachannel') => {
    switch (eventName) {
      case "user-published":
        //here agrs[1] = mediaType:'audio' | 'video' | 'datachannel'
        if (mediaType == "video") {
          // Retrieve the remote video track.
          this.channelParameters.remoteVideoTrack = user.videoTrack;
          // Retrieve the remote audio track.
          this.channelParameters.remoteAudioTrack = user.audioTrack;
          // Save the remote user id for reuse.
          this.channelParameters.remoteUser = user;
          this.channelParameters?.remoteVideoTrack?.play(this.remotePlayerContainer.nativeElement);
        }
        // Subscribe and play the remote audio track If the remote user publishes the audio track only.
        if (mediaType == "audio") {
          // Get the RemoteAudioTrack object in the AgoraRTCRemoteUser object.
          this.channelParameters.remoteAudioTrack = user.audioTrack;
          // Play the remote audio track. No need to pass any DOM element.
          this.channelParameters?.remoteAudioTrack?.play();
        }
    }
  };

  async leave() {
    // Destroy the local audio and video tracks.
    this.channelParameters?.localAudioTrack?.close();
    this.channelParameters?.localVideoTrack?.close();
    // Remove the containers you created for the local video and remote video.
    await this.agoraEngine?.leave();
  };


  protected readonly faEnvelope = faEnvelope;
  protected readonly faPlay = faPlay;
  protected readonly faMicrophoneSlash = faMicrophoneSlash;
  protected readonly faMicrophone = faMicrophone;
  protected readonly faVideoSlash = faVideoSlash;
  protected readonly faVideo = faVideo;
  protected readonly faPhoneSlash = faPhoneSlash;
}
