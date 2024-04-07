package com.interview.me.web.rest;

import com.interview.me.cvparser.CvParserService;
import com.interview.me.domain.*;
import com.interview.me.repository.AppointmentRepository;
import com.interview.me.repository.IntervieweeRepository;
import com.interview.me.service.*;
import com.interview.me.service.criteria.AppointmentCriteria;
import com.interview.me.service.criteria.IntervieweeCriteria;
import com.interview.me.service.dto.AdminUserDTO;
import com.interview.me.web.rest.errors.BadRequestAlertException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Interviewee}.
 */
@RestController
@RequestMapping("/api/video-call")
public class VideoCallResource {

    private final Logger log = LoggerFactory.getLogger(VideoCallResource.class);

    private final AppointmentRepository appointmentRepository;
    private final AgoraTokenService agoraTokenService;

    public VideoCallResource(AppointmentRepository appointmentRepository, AgoraTokenService agoraTokenService) {
        this.appointmentRepository = appointmentRepository;
        this.agoraTokenService = agoraTokenService;
    }

    @GetMapping("/getToken")
    public AgoraAuthDetail getToken(@RequestParam String channelName, @RequestParam int userUid) {
        Optional<Appointment> appointment = appointmentRepository.findAppointmentByAppointmentUid(channelName);
        if(appointment.isPresent()){
           Appointment appointmentX = appointment.get();

           if(appointmentX.getToken() != null && !appointmentX.getToken().isEmpty()){
               return agoraTokenService.getToken(channelName, false);
           }else{
               return agoraTokenService.getToken(channelName, true);
           }
       }

        return null;

    }

}
