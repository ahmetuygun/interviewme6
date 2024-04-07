package com.interview.me.web.rest;

import com.interview.me.domain.PersonalDetail;
import com.interview.me.repository.PersonalDetailRepository;
import com.interview.me.service.PersonalDetailQueryService;
import com.interview.me.service.PersonalDetailService;
import com.interview.me.service.criteria.PersonalDetailCriteria;
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
 * REST controller for managing {@link com.interview.me.domain.PersonalDetail}.
 */
@RestController
@RequestMapping("/api/personal-details")
public class PersonalDetailResource {

    private final Logger log = LoggerFactory.getLogger(PersonalDetailResource.class);

    private static final String ENTITY_NAME = "personalDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalDetailService personalDetailService;

    private final PersonalDetailRepository personalDetailRepository;

    private final PersonalDetailQueryService personalDetailQueryService;

    public PersonalDetailResource(
        PersonalDetailService personalDetailService,
        PersonalDetailRepository personalDetailRepository,
        PersonalDetailQueryService personalDetailQueryService
    ) {
        this.personalDetailService = personalDetailService;
        this.personalDetailRepository = personalDetailRepository;
        this.personalDetailQueryService = personalDetailQueryService;
    }

    /**
     * {@code POST  /personal-details} : Create a new personalDetail.
     *
     * @param personalDetail the personalDetail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalDetail, or with status {@code 400 (Bad Request)} if the personalDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PersonalDetail> createPersonalDetail(@RequestBody PersonalDetail personalDetail) throws URISyntaxException {
        log.debug("REST request to save PersonalDetail : {}", personalDetail);
        if (personalDetail.getId() != null) {
            throw new BadRequestAlertException("A new personalDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalDetail result = personalDetailService.save(personalDetail);
        return ResponseEntity
            .created(new URI("/api/personal-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-details/:id} : Updates an existing personalDetail.
     *
     * @param id the id of the personalDetail to save.
     * @param personalDetail the personalDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalDetail,
     * or with status {@code 400 (Bad Request)} if the personalDetail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonalDetail> updatePersonalDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalDetail personalDetail
    ) throws URISyntaxException {
        log.debug("REST request to update PersonalDetail : {}, {}", id, personalDetail);
        if (personalDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalDetail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonalDetail result = personalDetailService.update(personalDetail);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalDetail.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personal-details/:id} : Partial updates given fields of an existing personalDetail, field will ignore if it is null
     *
     * @param id the id of the personalDetail to save.
     * @param personalDetail the personalDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalDetail,
     * or with status {@code 400 (Bad Request)} if the personalDetail is not valid,
     * or with status {@code 404 (Not Found)} if the personalDetail is not found,
     * or with status {@code 500 (Internal Server Error)} if the personalDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonalDetail> partialUpdatePersonalDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalDetail personalDetail
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonalDetail partially : {}, {}", id, personalDetail);
        if (personalDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalDetail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonalDetail> result = personalDetailService.partialUpdate(personalDetail);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalDetail.getId().toString())
        );
    }

    /**
     * {@code GET  /personal-details} : get all the personalDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PersonalDetail>> getAllPersonalDetails(
        PersonalDetailCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PersonalDetails by criteria: {}", criteria);

        Page<PersonalDetail> page = personalDetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personal-details/count} : count all the personalDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPersonalDetails(PersonalDetailCriteria criteria) {
        log.debug("REST request to count PersonalDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(personalDetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /personal-details/:id} : get the "id" personalDetail.
     *
     * @param id the id of the personalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalDetail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonalDetail> getPersonalDetail(@PathVariable("id") Long id) {
        log.debug("REST request to get PersonalDetail : {}", id);
        Optional<PersonalDetail> personalDetail = personalDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalDetail);
    }

    /**
     * {@code DELETE  /personal-details/:id} : delete the "id" personalDetail.
     *
     * @param id the id of the personalDetail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonalDetail(@PathVariable("id") Long id) {
        log.debug("REST request to delete PersonalDetail : {}", id);
        personalDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
