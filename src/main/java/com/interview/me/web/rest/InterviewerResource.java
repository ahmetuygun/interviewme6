package com.interview.me.web.rest;

import com.interview.me.cvparser.CvParserService;
import com.interview.me.domain.Interviewer;
import com.interview.me.domain.PersonalDetail;
import com.interview.me.domain.User;
import com.interview.me.repository.InterviewerRepository;
import com.interview.me.security.SecurityUtils;
import com.interview.me.service.InterviewerQueryService;
import com.interview.me.service.InterviewerService;
import com.interview.me.service.PersonalDetailService;
import com.interview.me.service.UserService;
import com.interview.me.service.criteria.InterviewerCriteria;
import com.interview.me.service.dto.AdminUserDTO;
import com.interview.me.web.rest.errors.BadRequestAlertException;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.interview.me.domain.Interviewer}.
 */
@RestController
@RequestMapping("/api/interviewers")
public class InterviewerResource {

    private final Logger log = LoggerFactory.getLogger(InterviewerResource.class);

    private static final String ENTITY_NAME = "interviewer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewerService interviewerService;

    private final InterviewerRepository interviewerRepository;

    private final InterviewerQueryService interviewerQueryService;

    private final CvParserService cvParserService;


    private final PersonalDetailService personalDetailService;


    private final UserService userService;




    public InterviewerResource(
            InterviewerService interviewerService,
            InterviewerRepository interviewerRepository,
            InterviewerQueryService interviewerQueryService, CvParserService cvParserService, PersonalDetailService personalDetailService, UserService userService
    ) {
        this.interviewerService = interviewerService;
        this.interviewerRepository = interviewerRepository;
        this.interviewerQueryService = interviewerQueryService;
        this.cvParserService = cvParserService;
        this.personalDetailService = personalDetailService;
        this.userService = userService;
    }

    /**
     * {@code POST  /interviewers} : Create a new interviewer.
     *
     * @param interviewer the interviewer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewer, or with status {@code 400 (Bad Request)} if the interviewer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Interviewer> createInterviewer(@RequestBody Interviewer interviewer) throws URISyntaxException {
        log.debug("REST request to save Interviewer : {}", interviewer);
        if (interviewer.getId() != null) {
            throw new BadRequestAlertException("A new interviewer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            AdminUserDTO userDTO =   userService
                .getUserWithAuthorities()
                .map(AdminUserDTO::new)
                .orElseThrow(() ->  new RuntimeException("User not found"));


            PersonalDetail personalDetail = cvParserService.parse(interviewer.getCv(),interviewer.getCvContentType());
            personalDetail = personalDetailService.save(personalDetail);
            interviewer.setInternalUser(new User(userDTO.getId()));
            interviewer.setPersonalDetail(personalDetail);
            interviewer = interviewerService.save(interviewer);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/interviewers/" + interviewer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, interviewer.getId().toString()))
            .body(interviewer);
    }

    /**
     * {@code PUT  /interviewers/:id} : Updates an existing interviewer.
     *
     * @param id the id of the interviewer to save.
     * @param interviewer the interviewer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewer,
     * or with status {@code 400 (Bad Request)} if the interviewer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Interviewer> updateInterviewer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Interviewer interviewer
    ) throws URISyntaxException {
        log.debug("REST request to update Interviewer : {}, {}", id, interviewer);
        if (interviewer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Interviewer result = interviewerService.update(interviewer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interviewers/:id} : Partial updates given fields of an existing interviewer, field will ignore if it is null
     *
     * @param id the id of the interviewer to save.
     * @param interviewer the interviewer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewer,
     * or with status {@code 400 (Bad Request)} if the interviewer is not valid,
     * or with status {@code 404 (Not Found)} if the interviewer is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Interviewer> partialUpdateInterviewer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Interviewer interviewer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Interviewer partially : {}, {}", id, interviewer);
        if (interviewer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Interviewer> result = interviewerService.partialUpdate(interviewer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewer.getId().toString())
        );
    }

    /**
     * {@code GET  /interviewers} : get all the interviewers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Interviewer>> getAllInterviewers(
        InterviewerCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Interviewers by criteria: {}", criteria);

        Page<Interviewer> page = interviewerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interviewers/count} : count all the interviewers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInterviewers(InterviewerCriteria criteria) {
        log.debug("REST request to count Interviewers by criteria: {}", criteria);
        return ResponseEntity.ok().body(interviewerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interviewers/:id} : get the "id" interviewer.
     *
     * @param id the id of the interviewer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Interviewer> getInterviewer(@PathVariable("id") Long id) {
        log.debug("REST request to get Interviewer : {}", id);
        Optional<Interviewer> interviewer = interviewerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewer);
    }

    /**
     * {@code DELETE  /interviewers/:id} : delete the "id" interviewer.
     *
     * @param id the id of the interviewer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Interviewer : {}", id);
        interviewerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
