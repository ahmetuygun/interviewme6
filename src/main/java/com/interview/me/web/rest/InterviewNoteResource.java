package com.interview.me.web.rest;

import com.interview.me.domain.InterviewNote;
import com.interview.me.repository.InterviewNoteRepository;
import com.interview.me.service.InterviewNoteQueryService;
import com.interview.me.service.InterviewNoteService;
import com.interview.me.service.criteria.InterviewNoteCriteria;
import com.interview.me.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.interview.me.domain.InterviewNote}.
 */
@RestController
@RequestMapping("/api/interview-notes")
public class InterviewNoteResource {

    private final Logger log = LoggerFactory.getLogger(InterviewNoteResource.class);

    private static final String ENTITY_NAME = "interviewNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewNoteService interviewNoteService;

    private final InterviewNoteRepository interviewNoteRepository;

    private final InterviewNoteQueryService interviewNoteQueryService;

    public InterviewNoteResource(
        InterviewNoteService interviewNoteService,
        InterviewNoteRepository interviewNoteRepository,
        InterviewNoteQueryService interviewNoteQueryService
    ) {
        this.interviewNoteService = interviewNoteService;
        this.interviewNoteRepository = interviewNoteRepository;
        this.interviewNoteQueryService = interviewNoteQueryService;
    }

    /**
     * {@code POST  /interview-notes} : Create a new interviewNote.
     *
     * @param interviewNote the interviewNote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewNote, or with status {@code 400 (Bad Request)} if the interviewNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InterviewNote> createInterviewNote(@RequestBody InterviewNote interviewNote) throws URISyntaxException {
        log.debug("REST request to save InterviewNote : {}", interviewNote);
        if (interviewNote.getId() != null) {
            throw new BadRequestAlertException("A new interviewNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewNote result = interviewNoteService.save(interviewNote);
        return ResponseEntity
            .created(new URI("/api/interview-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-notes/:id} : Updates an existing interviewNote.
     *
     * @param id the id of the interviewNote to save.
     * @param interviewNote the interviewNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewNote,
     * or with status {@code 400 (Bad Request)} if the interviewNote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InterviewNote> updateInterviewNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InterviewNote interviewNote
    ) throws URISyntaxException {
        log.debug("REST request to update InterviewNote : {}, {}", id, interviewNote);
        if (interviewNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewNote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InterviewNote result = interviewNoteService.update(interviewNote);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewNote.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interview-notes/:id} : Partial updates given fields of an existing interviewNote, field will ignore if it is null
     *
     * @param id the id of the interviewNote to save.
     * @param interviewNote the interviewNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewNote,
     * or with status {@code 400 (Bad Request)} if the interviewNote is not valid,
     * or with status {@code 404 (Not Found)} if the interviewNote is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InterviewNote> partialUpdateInterviewNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InterviewNote interviewNote
    ) throws URISyntaxException {
        log.debug("REST request to partial update InterviewNote partially : {}, {}", id, interviewNote);
        if (interviewNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviewNote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InterviewNote> result = interviewNoteService.partialUpdate(interviewNote);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewNote.getId().toString())
        );
    }

    /**
     * {@code GET  /interview-notes} : get all the interviewNotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewNotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InterviewNote>> getAllInterviewNotes(
        InterviewNoteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InterviewNotes by criteria: {}", criteria);

        Page<InterviewNote> page = interviewNoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interview-notes/count} : count all the interviewNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInterviewNotes(InterviewNoteCriteria criteria) {
        log.debug("REST request to count InterviewNotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(interviewNoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interview-notes/:id} : get the "id" interviewNote.
     *
     * @param id the id of the interviewNote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewNote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InterviewNote> getInterviewNote(@PathVariable("id") Long id) {
        log.debug("REST request to get InterviewNote : {}", id);
        Optional<InterviewNote> interviewNote = interviewNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewNote);
    }

    /**
     * {@code DELETE  /interview-notes/:id} : delete the "id" interviewNote.
     *
     * @param id the id of the interviewNote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewNote(@PathVariable("id") Long id) {
        log.debug("REST request to delete InterviewNote : {}", id);
        interviewNoteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
