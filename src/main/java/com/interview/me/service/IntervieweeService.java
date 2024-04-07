package com.interview.me.service;

import com.interview.me.domain.Interviewee;
import com.interview.me.repository.IntervieweeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.Interviewee}.
 */
@Service
@Transactional
public class IntervieweeService {

    private final Logger log = LoggerFactory.getLogger(IntervieweeService.class);

    private final IntervieweeRepository intervieweeRepository;

    public IntervieweeService(IntervieweeRepository intervieweeRepository) {
        this.intervieweeRepository = intervieweeRepository;
    }

    /**
     * Save a interviewee.
     *
     * @param interviewee the entity to save.
     * @return the persisted entity.
     */
    public Interviewee save(Interviewee interviewee) {
        log.debug("Request to save Interviewee : {}", interviewee);
        return intervieweeRepository.save(interviewee);
    }

    /**
     * Update a interviewee.
     *
     * @param interviewee the entity to save.
     * @return the persisted entity.
     */
    public Interviewee update(Interviewee interviewee) {
        log.debug("Request to update Interviewee : {}", interviewee);
        return intervieweeRepository.save(interviewee);
    }

    /**
     * Partially update a interviewee.
     *
     * @param interviewee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Interviewee> partialUpdate(Interviewee interviewee) {
        log.debug("Request to partially update Interviewee : {}", interviewee);

        return intervieweeRepository
            .findById(interviewee.getId())
            .map(existingInterviewee -> {
                if (interviewee.getPhoto() != null) {
                    existingInterviewee.setPhoto(interviewee.getPhoto());
                }
                if (interviewee.getPhotoContentType() != null) {
                    existingInterviewee.setPhotoContentType(interviewee.getPhotoContentType());
                }

                return existingInterviewee;
            })
            .map(intervieweeRepository::save);
    }

    /**
     * Get all the interviewees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Interviewee> findAll(Pageable pageable) {
        log.debug("Request to get all Interviewees");
        return intervieweeRepository.findAll(pageable);
    }

    /**
     * Get one interviewee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Interviewee> findOne(Long id) {
        log.debug("Request to get Interviewee : {}", id);
        return intervieweeRepository.findById(id);
    }

    /**
     * Delete the interviewee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Interviewee : {}", id);
        intervieweeRepository.deleteById(id);
    }
}
