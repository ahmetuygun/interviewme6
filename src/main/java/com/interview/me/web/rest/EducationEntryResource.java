package com.interview.me.web.rest;

import com.interview.me.domain.EducationEntry;
import com.interview.me.repository.EducationEntryRepository;
import com.interview.me.service.EducationEntryQueryService;
import com.interview.me.service.EducationEntryService;
import com.interview.me.service.criteria.EducationEntryCriteria;
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
 * REST controller for managing {@link com.interview.me.domain.EducationEntry}.
 */
@RestController
@RequestMapping("/api/education-entries")
public class EducationEntryResource {

    private final Logger log = LoggerFactory.getLogger(EducationEntryResource.class);

    private static final String ENTITY_NAME = "educationEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationEntryService educationEntryService;

    private final EducationEntryRepository educationEntryRepository;

    private final EducationEntryQueryService educationEntryQueryService;

    public EducationEntryResource(
        EducationEntryService educationEntryService,
        EducationEntryRepository educationEntryRepository,
        EducationEntryQueryService educationEntryQueryService
    ) {
        this.educationEntryService = educationEntryService;
        this.educationEntryRepository = educationEntryRepository;
        this.educationEntryQueryService = educationEntryQueryService;
    }

    /**
     * {@code POST  /education-entries} : Create a new educationEntry.
     *
     * @param educationEntry the educationEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationEntry, or with status {@code 400 (Bad Request)} if the educationEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EducationEntry> createEducationEntry(@RequestBody EducationEntry educationEntry) throws URISyntaxException {
        log.debug("REST request to save EducationEntry : {}", educationEntry);
        if (educationEntry.getId() != null) {
            throw new BadRequestAlertException("A new educationEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationEntry result = educationEntryService.save(educationEntry);
        return ResponseEntity
            .created(new URI("/api/education-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /education-entries/:id} : Updates an existing educationEntry.
     *
     * @param id the id of the educationEntry to save.
     * @param educationEntry the educationEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationEntry,
     * or with status {@code 400 (Bad Request)} if the educationEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EducationEntry> updateEducationEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EducationEntry educationEntry
    ) throws URISyntaxException {
        log.debug("REST request to update EducationEntry : {}, {}", id, educationEntry);
        if (educationEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationEntry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EducationEntry result = educationEntryService.update(educationEntry);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /education-entries/:id} : Partial updates given fields of an existing educationEntry, field will ignore if it is null
     *
     * @param id the id of the educationEntry to save.
     * @param educationEntry the educationEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationEntry,
     * or with status {@code 400 (Bad Request)} if the educationEntry is not valid,
     * or with status {@code 404 (Not Found)} if the educationEntry is not found,
     * or with status {@code 500 (Internal Server Error)} if the educationEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EducationEntry> partialUpdateEducationEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EducationEntry educationEntry
    ) throws URISyntaxException {
        log.debug("REST request to partial update EducationEntry partially : {}, {}", id, educationEntry);
        if (educationEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationEntry.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EducationEntry> result = educationEntryService.partialUpdate(educationEntry);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationEntry.getId().toString())
        );
    }

    /**
     * {@code GET  /education-entries} : get all the educationEntries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationEntries in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EducationEntry>> getAllEducationEntries(
        EducationEntryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EducationEntries by criteria: {}", criteria);

        Page<EducationEntry> page = educationEntryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /education-entries/count} : count all the educationEntries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEducationEntries(EducationEntryCriteria criteria) {
        log.debug("REST request to count EducationEntries by criteria: {}", criteria);
        return ResponseEntity.ok().body(educationEntryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /education-entries/:id} : get the "id" educationEntry.
     *
     * @param id the id of the educationEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EducationEntry> getEducationEntry(@PathVariable("id") Long id) {
        log.debug("REST request to get EducationEntry : {}", id);
        Optional<EducationEntry> educationEntry = educationEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationEntry);
    }

    /**
     * {@code DELETE  /education-entries/:id} : delete the "id" educationEntry.
     *
     * @param id the id of the educationEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducationEntry(@PathVariable("id") Long id) {
        log.debug("REST request to delete EducationEntry : {}", id);
        educationEntryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
