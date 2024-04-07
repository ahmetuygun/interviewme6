package com.interview.me.service;

import com.interview.me.domain.AgoraAuthDetail;
import io.agora.media.RtcTokenBuilder2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AgoraTokenService {


    @Value("${agora.appId}")
    private String appId;


    @Value("${agora.certificate}")
    private String appCertificate;
    static int tokenExpirationInSeconds = 3600;
    static int privilegeExpirationInSeconds = 3600;

    public AgoraAuthDetail getToken(String channelName, boolean pub){

        System.out.printf("App Id: %s\n", appId);
        System.out.printf("App Certificate: %s\n", appCertificate);
        if (appId == null || appId.isEmpty() || appCertificate == null || appCertificate.isEmpty()) {
            System.out.printf("Need to set environment variable AGORA_APP_ID and AGORA_APP_CERTIFICATE\n");
            return null;
        }

        RtcTokenBuilder2 token = new RtcTokenBuilder2();
        String result = token.buildTokenWithUid(appId, appCertificate, channelName, 0, pub ? RtcTokenBuilder2.Role.ROLE_PUBLISHER : RtcTokenBuilder2.Role.ROLE_SUBSCRIBER ,
            tokenExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.printf("Token with uid: %s\n", result);

        return new AgoraAuthDetail(result,appId );



    }


}
