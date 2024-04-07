package com.interview.me.web.rest;

import com.interview.me.domain.WorkExperienceEntry;
import com.interview.me.repository.WorkExperienceEntryRepository;
import com.interview.me.service.WorkExperienceEntryQueryService;
import com.interview.me.service.WorkExperienceEntryService;
import com.interview.me.service.criteria.WorkExperienceEntryCriteria;
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
 * REST controller for managing {@link com.interview.me.domain.WorkExperienceEntry}.
 */
@RestController
@RequestMapping("/api/work-experience-entries")
public class WorkExperienceEntryResource {

    private final Logger log = LoggerFactory.getLogger(WorkExperienceEntryResource.class);

    private static final String ENTITY_NAME = "workExperienceEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkExperienceEntryService workExperienceEntryService;

    private final WorkExperienceEntryRepository workExperienceEntryRepository;

    private final WorkExperienceEntryQueryService workExperienceEntryQueryService;

    public WorkExperienceEntryResource(
        WorkExperienceEntryService workExperienceEntryService,
        WorkExperienceEntryRepository workExperienceEntryRepository,
        WorkExperienceEntryQueryService workExperienceEntryQueryService
    ) {
        this.workExperienceEntryService = workExperienceEntryService;
        this.workExperienceEntryRepository = workExperienceEntryRepository;
        this.workExperienceEntryQueryService = workExperienceEntryQueryService;
    }

    /**
     * {@code POST  /work-experience-entries} : Create a new workExperienceEntry.
     *
     * @param workExperienceEntry the workExperienceEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workExperienceEntry, or with status {@code 400 (Bad Request)} if the workExperienceEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WorkExperienceEntry> createWorkExperienceEntry(@RequestBody WorkExperienceEntry workExperienceEntry)
        throws URISyntaxException {
        log.debug("REST request to save WorkExperienceEntry : {}", workExperienceEntry);
        if (workExperienceEntry.getId() != null) {
            throw new BadRequestAlertException("A new workExperienceEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkExperienceEntry result = workExperienceEntryService.save(workExperienceEntry);
        return ResponseEntity
            .created(new URI("/api/work-experience-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-experience-entries/:id} : Updates an existing workExperienceEntry.
     *
     * @param id the id of the workExperienceEntry to save.
     * @param workExperienceEntry the workExperienceEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workExperienceEntry,
     * or with status {@code 400 (Bad Request)} if the workExperienceEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workExperienceEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkExperienceEntry> updateWorkExperienceEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkExperienceEntry workExperienceEntry
    ) throws URISyntaxException {
        log.debug("REST request to update WorkExperienceEntry : {}, {}", id, workExperienceEntry);
        if (workExperienceEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workExperienceEntry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workExperienceEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkExperienceEntry result = workExperienceEntryService.update(workExperienceEntry);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workExperienceEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-experience-entries/:id} : Partial updates given fields of an existing workExperienceEntry, field will ignore if it is null
     *
     * @param id the id of the workExperienceEntry to save.
     * @param workExperienceEntry the workExperienceEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workExperienceEntry,
     * or with status {@code 400 (Bad Request)} if the workExperienceEntry is not valid,
     * or with status {@code 404 (Not Found)} if the workExperienceEntry is not found,
     * or with status {@code 500 (Internal Server Error)} if the workExperienceEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkExperienceEntry> partialUpdateWorkExperienceEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkExperienceEntry workExperienceEntry
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkExperienceEntry partially : {}, {}", id, workExperienceEntry);
        if (workExperienceEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workExperienceEntry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workExperienceEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkExperienceEntry> result = workExperienceEntryService.partialUpdate(workExperienceEntry);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workExperienceEntry.getId().toString())
        );
    }

    /**
     * {@code GET  /work-experience-entries} : get all the workExperienceEntries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workExperienceEntries in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WorkExperienceEntry>> getAllWorkExperienceEntries(
        WorkExperienceEntryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkExperienceEntries by criteria: {}", criteria);

        Page<WorkExperienceEntry> page = workExperienceEntryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-experience-entries/count} : count all the workExperienceEntries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWorkExperienceEntries(WorkExperienceEntryCriteria criteria) {
        log.debug("REST request to count WorkExperienceEntries by criteria: {}", criteria);
        return ResponseEntity.ok().body(workExperienceEntryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-experience-entries/:id} : get the "id" workExperienceEntry.
     *
     * @param id the id of the workExperienceEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workExperienceEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkExperienceEntry> getWorkExperienceEntry(@PathVariable("id") Long id) {
        log.debug("REST request to get WorkExperienceEntry : {}", id);
        Optional<WorkExperienceEntry> workExperienceEntry = workExperienceEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workExperienceEntry);
    }

    /**
     * {@code DELETE  /work-experience-entries/:id} : delete the "id" workExperienceEntry.
     *
     * @param id the id of the workExperienceEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkExperienceEntry(@PathVariable("id") Long id) {
        log.debug("REST request to delete WorkExperienceEntry : {}", id);
        workExperienceEntryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
