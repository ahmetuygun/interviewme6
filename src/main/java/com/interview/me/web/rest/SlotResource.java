package com.interview.me.web.rest;

import com.interview.me.domain.Interviewer;
import com.interview.me.domain.Slot;
import com.interview.me.repository.SlotRepository;
import com.interview.me.service.SlotQueryService;
import com.interview.me.service.SlotService;
import com.interview.me.service.criteria.SlotCriteria;
import com.interview.me.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.nimbusds.jose.shaded.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONObject;
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
 * REST controller for managing {@link com.interview.me.domain.Slot}.
 */
@RestController
@RequestMapping("/api/slots")
public class SlotResource {

    private static final String ENTITY_NAME = "slot";
    private final Logger log = LoggerFactory.getLogger(SlotResource.class);
    private final SlotService slotService;
    private final SlotRepository slotRepository;
    private final SlotQueryService slotQueryService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public SlotResource(SlotService slotService, SlotRepository slotRepository, SlotQueryService slotQueryService) {
        this.slotService = slotService;
        this.slotRepository = slotRepository;
        this.slotQueryService = slotQueryService;
    }

    @PostMapping("")
    public ResponseEntity<Slot> createSlot(@RequestBody String slot) throws URISyntaxException {


        JSONObject raw_data = new JSONObject(slot);
        JSONArray slots = raw_data.getJSONArray("slots");
        Long interviewerId =  raw_data.getLong("interviewerId");



        List<Slot> newSlotList = StreamSupport.stream(slots.spliterator(), false)
            .map(val -> toSlot( (JSONObject) val, interviewerId))
            .collect(Collectors.toList());


        LocalDate currentDate = LocalDate.now();
        List<Slot> currentSlotList = slotRepository.findByInterviewer_IdAndYearAndMonthAndDayIsGreaterThanEqual(
            interviewerId,
            currentDate.getYear(),
            currentDate.getMonthValue(),
            currentDate.getDayOfMonth()
        );

        List<Slot> slotListToBeUpdated = synchSlotList(currentSlotList,newSlotList);
        slotListToBeUpdated.forEach(slotService::save);



        return ResponseEntity
            .created(new URI("/api/slots/" + interviewerId))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, slotListToBeUpdated.get(0).getId().toString()))
            .body(slotListToBeUpdated.get(0));
    }

    private List<Slot>  synchSlotList(List<Slot> currentSlotList, List<Slot> newSlotList) {

        List<Slot> slotListToBeUpdated = new ArrayList<>();
        for (Slot newSlot  : newSlotList) {
            if(!isSlotContains(currentSlotList,newSlot)){
                slotListToBeUpdated.add(newSlot);
            }
        }

        for (Slot current : currentSlotList) {
            for (Slot newSlot : newSlotList) {
                if (current.getYear() == newSlot.getYear() &&
                    current.getMonth() == newSlot.getMonth() &&
                    current.getDay() == newSlot.getDay()) {
                    current.setAvailableHours(newSlot.getAvailableHours());
                }
            }
        }

        slotListToBeUpdated.addAll(currentSlotList);
        return slotListToBeUpdated;
    }

    private boolean isSlotContains(List<Slot> list, Slot item) {

        for (Slot current : list) {
            if (current.getYear() == item.getYear() &&
                current.getMonth() == item.getMonth() &&
                current.getDay() == item.getDay()) {
                return true;
            }
        }
        return false;

    }


    private Slot toSlot(JSONObject val, Long interviewerId) {
        JSONObject date =   ((JSONObject) val).getJSONObject("date");
        Slot slot = new Slot();
        slot.setDay((Integer) date.get("day"));
        slot.setMonth((Integer) date.get("month"));
        slot.setYear((Integer) date.get("year"));

        List<String> availableHours = StreamSupport.stream(val.getJSONArray("availableHours")
                .spliterator(), false)
            .map(item -> item.toString())
            .collect(Collectors.toList());

        slot.setAvailableHours(availableHours);
        slot.setInterviewer(new Interviewer(interviewerId));
        return slot;

    }

    /**
     * {@code PUT  /slots/:id} : Updates an existing slot.
     *
     * @param id   the id of the slot to save.
     * @param slot the slot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slot,
     * or with status {@code 400 (Bad Request)} if the slot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the slot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Slot> updateSlot(@PathVariable(value = "id", required = false) final Long id, @RequestBody Slot slot)
        throws URISyntaxException {
        log.debug("REST request to update Slot : {}, {}", id, slot);
        if (slot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, slot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!slotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Slot result = slotService.update(slot);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, slot.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /slots/:id} : Partial updates given fields of an existing slot, field will ignore if it is null
     *
     * @param id   the id of the slot to save.
     * @param slot the slot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slot,
     * or with status {@code 400 (Bad Request)} if the slot is not valid,
     * or with status {@code 404 (Not Found)} if the slot is not found,
     * or with status {@code 500 (Internal Server Error)} if the slot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Slot> partialUpdateSlot(@PathVariable(value = "id", required = false) final Long id, @RequestBody Slot slot)
        throws URISyntaxException {
        log.debug("REST request to partial update Slot partially : {}, {}", id, slot);
        if (slot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, slot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!slotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Slot> result = slotService.partialUpdate(slot);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, slot.getId().toString())
        );
    }

    /**
     * {@code GET  /slots} : get all the slots.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slots in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Slot>> getAllSlots(
        SlotCriteria criteria, // you need to retreive slots with ids
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Slots by criteria: {}", criteria);

        Page<Slot> page = slotQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /slots/count} : count all the slots.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSlots(SlotCriteria criteria) {
        log.debug("REST request to count Slots by criteria: {}", criteria);
        return ResponseEntity.ok().body(slotQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /slots/:id} : get the "id" slot.
     *
     * @param id the id of the slot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the slot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Slot> getSlot(@PathVariable("id") Long id) {
        log.debug("REST request to get Slot : {}", id);
        Optional<Slot> slot = slotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(slot);
    }

    /**
     * {@code DELETE  /slots/:id} : delete the "id" slot.
     *
     * @param id the id of the slot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable("id") Long id) {
        log.debug("REST request to delete Slot : {}", id);
        slotService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /slots} : Create a new slot.
     *
     * @param slot the slot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new slot, or with status {@code 400 (Bad Request)} if the slot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    public class TimeSlot {
        private List<Integer> date; // You can use a list to represent date: [year, month, day]
        private List<String> availableHours;
        private boolean enabled;

        public List<Integer> getDate() {
            return date;
        }

        public void setDate(List<Integer> date) {
            this.date = date;
        }

        public List<String> getAvailableHours() {
            return availableHours;
        }

        public void setAvailableHours(List<String> availableHours) {
            this.availableHours = availableHours;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

// Constructors, getters, and setters
    }

    public class SlotX {
        private List<TimeSlot> slots;
        private Interviewer interviewer;

        public List<TimeSlot> getSlots() {
            return slots;
        }

        public void setSlots(List<TimeSlot> slots) {
            this.slots = slots;
        }

        public Interviewer getInterviewer() {
            return interviewer;
        }

        public void setInterviewer(Interviewer interviewer) {
            this.interviewer = interviewer;
        }
        // Constructors, getters, and setters
    }
}
