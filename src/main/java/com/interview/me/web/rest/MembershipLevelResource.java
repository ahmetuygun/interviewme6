package com.interview.me.web.rest;

import com.interview.me.domain.MembershipLevel;
import com.interview.me.repository.MembershipLevelRepository;
import com.interview.me.service.MembershipLevelService;
import com.interview.me.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.interview.me.domain.MembershipLevel}.
 */
@RestController
@RequestMapping("/api/membership-levels")
public class MembershipLevelResource {

    private final Logger log = LoggerFactory.getLogger(MembershipLevelResource.class);

    private static final String ENTITY_NAME = "membershipLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipLevelService membershipLevelService;

    private final MembershipLevelRepository membershipLevelRepository;

    public MembershipLevelResource(MembershipLevelService membershipLevelService, MembershipLevelRepository membershipLevelRepository) {
        this.membershipLevelService = membershipLevelService;
        this.membershipLevelRepository = membershipLevelRepository;
    }

    /**
     * {@code POST  /membership-levels} : Create a new membershipLevel.
     *
     * @param membershipLevel the membershipLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membershipLevel, or with status {@code 400 (Bad Request)} if the membershipLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MembershipLevel> createMembershipLevel(@RequestBody MembershipLevel membershipLevel) throws URISyntaxException {
        log.debug("REST request to save MembershipLevel : {}", membershipLevel);
        if (membershipLevel.getId() != null) {
            throw new BadRequestAlertException("A new membershipLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipLevel result = membershipLevelService.save(membershipLevel);
        return ResponseEntity
            .created(new URI("/api/membership-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membership-levels/:id} : Updates an existing membershipLevel.
     *
     * @param id the id of the membershipLevel to save.
     * @param membershipLevel the membershipLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipLevel,
     * or with status {@code 400 (Bad Request)} if the membershipLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membershipLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MembershipLevel> updateMembershipLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MembershipLevel membershipLevel
    ) throws URISyntaxException {
        log.debug("REST request to update MembershipLevel : {}, {}", id, membershipLevel);
        if (membershipLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membershipLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membershipLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MembershipLevel result = membershipLevelService.update(membershipLevel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /membership-levels/:id} : Partial updates given fields of an existing membershipLevel, field will ignore if it is null
     *
     * @param id the id of the membershipLevel to save.
     * @param membershipLevel the membershipLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipLevel,
     * or with status {@code 400 (Bad Request)} if the membershipLevel is not valid,
     * or with status {@code 404 (Not Found)} if the membershipLevel is not found,
     * or with status {@code 500 (Internal Server Error)} if the membershipLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MembershipLevel> partialUpdateMembershipLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MembershipLevel membershipLevel
    ) throws URISyntaxException {
        log.debug("REST request to partial update MembershipLevel partially : {}, {}", id, membershipLevel);
        if (membershipLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membershipLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membershipLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MembershipLevel> result = membershipLevelService.partialUpdate(membershipLevel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membershipLevel.getId().toString())
        );
    }

    /**
     * {@code GET  /membership-levels} : get all the membershipLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membershipLevels in body.
     */
    @GetMapping("")
    public List<MembershipLevel> getAllMembershipLevels() {
        log.debug("REST request to get all MembershipLevels");
        return membershipLevelService.findAll();
    }

    /**
     * {@code GET  /membership-levels/:id} : get the "id" membershipLevel.
     *
     * @param id the id of the membershipLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membershipLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MembershipLevel> getMembershipLevel(@PathVariable("id") Long id) {
        log.debug("REST request to get MembershipLevel : {}", id);
        Optional<MembershipLevel> membershipLevel = membershipLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipLevel);
    }

    /**
     * {@code DELETE  /membership-levels/:id} : delete the "id" membershipLevel.
     *
     * @param id the id of the membershipLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembershipLevel(@PathVariable("id") Long id) {
        log.debug("REST request to delete MembershipLevel : {}", id);
        membershipLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
