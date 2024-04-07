package com.interview.me.web.rest;


import com.interview.me.domain.*;
import com.interview.me.repository.AppointmentRepository;
import com.interview.me.repository.SlotRepository;
import com.interview.me.service.AppointmentQueryService;
import com.interview.me.service.criteria.AppointmentCriteria;
import com.interview.me.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link Appointment}.
 */
@RestController
@RequestMapping("/api/appointments")
@Transactional
public class AppointmentResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentResource.class);

    private static final String ENTITY_NAME = "appointment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointmentRepository appointmentRepository;
    private final SlotRepository slotRepository;


    private final AppointmentQueryService appointmentQueryService;





    public AppointmentResource(AppointmentRepository appointmentRepository, SlotRepository slotRepository, AppointmentQueryService appointmentQueryService) {
        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
        this.appointmentQueryService = appointmentQueryService;
    }

    /**
     * {@code POST  /appointments} : Create a new appointment.
     *
     * @param appointment the appointment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointment, or with status {@code 400 (Bad Request)} if the appointment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) throws URISyntaxException {
        log.debug("REST request to save Appointment : {}", appointment);

        if (appointment.getInterviewee() == null) {
            throw new BadRequestAlertException("Interviewer info can be empty.", ENTITY_NAME, "idexists");
        }
        if (appointment.getInterviewer() == null) {
            throw new BadRequestAlertException("Interviewee info can be empty.", ENTITY_NAME, "idexists");
        }

        Optional<Slot> slotOptional = slotRepository.findByInterviewer_IdAndYearAndMonthAndDay(
            appointment.getInterviewer().getId(),
            appointment.getYear(),
            appointment.getMonth(),
            appointment.getDay()
        );

        Slot slot =  slotOptional.orElseThrow(()-> {
            throw new BadRequestAlertException("the slot is not available", ENTITY_NAME, "idexists");
        });
        List<String> hours = slot.getAvailableHours();
        if(slot.getAvailableHours().contains(appointment.getSlot())){
            hours.remove(appointment.getSlot());
            slot.setAvailableHours(hours);
            slotRepository.save(slot);
        }else {
            throw new BadRequestAlertException("the slot is not available", ENTITY_NAME, "idexists");
        }
        appointment.setAppointmentUid(UUID.randomUUID().toString());
        Appointment result = appointmentRepository.save(appointment);

        return ResponseEntity
            .created(new URI("/api/appointments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appointments/:id} : Updates an existing appointment.
     *
     * @param id the id of the appointment to save.
     * @param appointment the appointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment,
     * or with status {@code 400 (Bad Request)} if the appointment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appointment appointment
    ) throws URISyntaxException {
        log.debug("REST request to update Appointment : {}, {}", id, appointment);
        if (appointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Appointment result = appointmentRepository.save(appointment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /appointments/:id} : Partial updates given fields of an existing appointment, field will ignore if it is null
     *
     * @param id the id of the appointment to save.
     * @param appointment the appointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment,
     * or with status {@code 400 (Bad Request)} if the appointment is not valid,
     * or with status {@code 404 (Not Found)} if the appointment is not found,
     * or with status {@code 500 (Internal Server Error)} if the appointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appointment> partialUpdateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appointment appointment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Appointment partially : {}, {}", id, appointment);
        if (appointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Appointment> result = appointmentRepository
            .findById(appointment.getId())
            .map(existingAppointment -> {
                if (appointment.getStatus() != null) {
                    existingAppointment.setStatus(appointment.getStatus());
                }
                if (appointment.getYear() != null) {
                    existingAppointment.setYear(appointment.getYear());
                }
                if (appointment.getMonth() != null) {
                    existingAppointment.setMonth(appointment.getMonth());
                }
                if (appointment.getDay() != null) {
                    existingAppointment.setDay(appointment.getDay());
                }
                if (appointment.getSlot() != null) {
                    existingAppointment.setSlot(appointment.getSlot());
                }
                if (appointment.getAppointmentUid() != null) {
                    existingAppointment.setAppointmentUid(appointment.getAppointmentUid());
                }

                return existingAppointment;
            })
            .map(appointmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointment.getId().toString())
        );
    }

    /**
     * {@code GET  /appointments} : get all the appointments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Appointment>> getAllAppointments(
        AppointmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Appointments by criteria: {}", criteria);

        Page<Appointment> page = appointmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appointments/:id} : get the "id" appointment.
     *
     * @param id the id of the appointment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("id") Long id) {
        log.debug("REST request to get Appointment : {}", id);
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appointment);
    }

    /**
     * {@code DELETE  /appointments/:id} : delete the "id" appointment.
     *
     * @param id the id of the appointment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Appointment : {}", id);
        appointmentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
