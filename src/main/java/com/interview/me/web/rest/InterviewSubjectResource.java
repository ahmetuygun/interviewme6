package com.interview.me.web.rest;

import com.interview.me.domain.InterviewSubject;
import com.interview.me.repository.InterviewSubjectRepository;
import com.interview.me.service.InterviewSubjectQueryService;
import com.interview.me.service.InterviewSubjectService;
import com.interview.me.service.criteria.InterviewSubjectCriteria;
import com.interview.me.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.interview.me.domain.InterviewSubject}.
 */
@RestController
@RequestMapping("/api/interview-subjects")
public class InterviewSubjectResource {

    private final Logger log = LoggerFactory.getLogger(InterviewSubjectResource.class);

    private static final String ENTITY_NAME = "interviewSubject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewSubjectService interviewSubjectService;

    private final InterviewSubjectRepository interviewSubjectRepository;

    private final InterviewSubjectQueryService interviewSubjectQueryService;

    public InterviewSubjectResource(
        InterviewSubjectService interviewSubjectService,
        InterviewSubjectRepository interviewSubjectRepository,
        InterviewSubjectQueryService interviewSubjectQueryService
    ) {
        this.interviewSubjectService = interviewSubjectService;
        this.interviewSubjectRepository = interviewSubjectRepository;
        this.interviewSubjectQueryService = interviewSubjectQueryService;
    }

    /**
     * {@code POST  /interview-subjects} : Create a new interviewSubject.
     *
     * @param interviewSubject the interviewSubject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewSubject, or with status {@code 400 (Bad Request)} if the interviewSubject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InterviewSubject> createInterviewSubject(@Valid @RequestBody InterviewSubject interviewSubject)
        throws URISyntaxException {
        log.debug("REST request to save InterviewSubject : {}", interviewSubject);
        if (interviewSubject.getId() != null) {
            throw new BadRequestAlertException("A new interviewSubject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewSubject result = interviewSubjectService.save(interviewSubject);
        return ResponseEntity
            .created(new URI("/api/interview-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-subjects/:id} : Updates an existing interviewSubject.
     *
     * @param id the id of the interviewSubject to save.
     * @param interviewSubject the interviewSubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewSubject,
     * or with status {@code 400 (Bad Request)} if the interviewSubject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewSubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InterviewSubject> updateInterviewSubject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InterviewSubject interviewSubject
    ) throws URISyntaxException {
        log.debug("REST request to update InterviewSubject : {}, {}", id, interviewSubject);
        if (interviewSubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewSubject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewSubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InterviewSubject result = interviewSubjectService.update(interviewSubject);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewSubject.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interview-subjects/:id} : Partial updates given fields of an existing interviewSubject, field will ignore if it is null
     *
     * @param id the id of the interviewSubject to save.
     * @param interviewSubject the interviewSubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewSubject,
     * or with status {@code 400 (Bad Request)} if the interviewSubject is not valid,
     * or with status {@code 404 (Not Found)} if the interviewSubject is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewSubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InterviewSubject> partialUpdateInterviewSubject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InterviewSubject interviewSubject
    ) throws URISyntaxException {
        log.debug("REST request to partial update InterviewSubject partially : {}, {}", id, interviewSubject);
        if (interviewSubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewSubject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewSubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InterviewSubject> result = interviewSubjectService.partialUpdate(interviewSubject);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewSubject.getId().toString())
        );
    }

    /**
     * {@code GET  /interview-subjects} : get all the interviewSubjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewSubjects in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InterviewSubject>> getAllInterviewSubjects(
        InterviewSubjectCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InterviewSubjects by criteria: {}", criteria);

        Page<InterviewSubject> page = interviewSubjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interview-subjects/count} : count all the interviewSubjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInterviewSubjects(InterviewSubjectCriteria criteria) {
        log.debug("REST request to count InterviewSubjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(interviewSubjectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interview-subjects/:id} : get the "id" interviewSubject.
     *
     * @param id the id of the interviewSubject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewSubject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InterviewSubject> getInterviewSubject(@PathVariable("id") Long id) {
        log.debug("REST request to get InterviewSubject : {}", id);
        Optional<InterviewSubject> interviewSubject = interviewSubjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewSubject);
    }

    /**
     * {@code DELETE  /interview-subjects/:id} : delete the "id" interviewSubject.
     *
     * @param id the id of the interviewSubject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewSubject(@PathVariable("id") Long id) {
        log.debug("REST request to delete InterviewSubject : {}", id);
        interviewSubjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
