package com.interview.me.web.rest;

import com.interview.me.cvparser.CvParserService;
import com.interview.me.domain.Interviewee;
import com.interview.me.domain.PersonalDetail;
import com.interview.me.domain.User;
import com.interview.me.repository.IntervieweeRepository;
import com.interview.me.service.IntervieweeQueryService;
import com.interview.me.service.IntervieweeService;
import com.interview.me.service.PersonalDetailService;
import com.interview.me.service.UserService;
import com.interview.me.service.criteria.IntervieweeCriteria;
import com.interview.me.service.dto.AdminUserDTO;
import com.interview.me.web.rest.errors.BadRequestAlertException;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.interview.me.domain.Interviewee}.
 */
@RestController
@RequestMapping("/api/interviewees")
public class IntervieweeResource {

    private final Logger log = LoggerFactory.getLogger(IntervieweeResource.class);

    private static final String ENTITY_NAME = "interviewee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntervieweeService intervieweeService;

    private final IntervieweeRepository intervieweeRepository;

    private final IntervieweeQueryService intervieweeQueryService;

    private final UserService userService;

    private final CvParserService cvParserService;


    private final PersonalDetailService personalDetailService;




    public IntervieweeResource(
            IntervieweeService intervieweeService,
            IntervieweeRepository intervieweeRepository,
            IntervieweeQueryService intervieweeQueryService, UserService userService, CvParserService cvParserService, PersonalDetailService personalDetailService
    ) {
        this.intervieweeService = intervieweeService;
        this.intervieweeRepository = intervieweeRepository;
        this.intervieweeQueryService = intervieweeQueryService;
        this.userService = userService;
        this.cvParserService = cvParserService;
        this.personalDetailService = personalDetailService;
    }

    /**
     * {@code POST  /interviewees} : Create a new interviewee.
     *
     * @param interviewee the interviewee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewee, or with status {@code 400 (Bad Request)} if the interviewee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Interviewee> createInterviewee(@RequestBody Interviewee interviewee) throws URISyntaxException {
        log.debug("REST request to save Interviewee : {}", interviewee);
        if (interviewee.getId() != null) {
            throw new BadRequestAlertException("A new interviewee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            AdminUserDTO userDTO =   userService
                .getUserWithAuthorities()
                .map(AdminUserDTO::new)
                .orElseThrow(() ->  new RuntimeException("User not found"));


            PersonalDetail personalDetail = cvParserService.parse(interviewee.getCv(),interviewee.getCvContentType());
            personalDetail = personalDetailService.save(personalDetail);
            interviewee.setInternalUser(new User(userDTO.getId()));
            interviewee.setPersonalDetail(personalDetail);
            interviewee = intervieweeService.save(interviewee);

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Interviewee result = intervieweeService.save(interviewee);
        return ResponseEntity
            .created(new URI("/api/interviewees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interviewees/:id} : Updates an existing interviewee.
     *
     * @param id the id of the interviewee to save.
     * @param interviewee the interviewee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewee,
     * or with status {@code 400 (Bad Request)} if the interviewee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Interviewee> updateInterviewee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Interviewee interviewee
    ) throws URISyntaxException {
        log.debug("REST request to update Interviewee : {}, {}", id, interviewee);
        if (interviewee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intervieweeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Interviewee result = intervieweeService.update(interviewee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interviewees/:id} : Partial updates given fields of an existing interviewee, field will ignore if it is null
     *
     * @param id the id of the interviewee to save.
     * @param interviewee the interviewee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewee,
     * or with status {@code 400 (Bad Request)} if the interviewee is not valid,
     * or with status {@code 404 (Not Found)} if the interviewee is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Interviewee> partialUpdateInterviewee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Interviewee interviewee
    ) throws URISyntaxException {
        log.debug("REST request to partial update Interviewee partially : {}, {}", id, interviewee);
        if (interviewee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intervieweeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Interviewee> result = intervieweeService.partialUpdate(interviewee);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewee.getId().toString())
        );
    }

    /**
     * {@code GET  /interviewees} : get all the interviewees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewees in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Interviewee>> getAllInterviewees(
        IntervieweeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Interviewees by criteria: {}", criteria);

        Page<Interviewee> page = intervieweeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interviewees/count} : count all the interviewees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInterviewees(IntervieweeCriteria criteria) {
        log.debug("REST request to count Interviewees by criteria: {}", criteria);
        return ResponseEntity.ok().body(intervieweeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interviewees/:id} : get the "id" interviewee.
     *
     * @param id the id of the interviewee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Interviewee> getInterviewee(@PathVariable("id") Long id) {
        log.debug("REST request to get Interviewee : {}", id);
        Optional<Interviewee> interviewee = intervieweeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewee);
    }

    /**
     * {@code DELETE  /interviewees/:id} : delete the "id" interviewee.
     *
     * @param id the id of the interviewee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewee(@PathVariable("id") Long id) {
        log.debug("REST request to delete Interviewee : {}", id);
        intervieweeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
