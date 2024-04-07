package com.interview.me.service;

import com.interview.me.domain.Interviewer;
import com.interview.me.repository.InterviewerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.Interviewer}.
 */
@Service
@Transactional
public class InterviewerService {

    private final Logger log = LoggerFactory.getLogger(InterviewerService.class);

    private final InterviewerRepository interviewerRepository;

    public InterviewerService(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    /**
     * Save a interviewer.
     *
     * @param interviewer the entity to save.
     * @return the persisted entity.
     */
    public Interviewer save(Interviewer interviewer) {
        log.debug("Request to save Interviewer : {}", interviewer);
        return interviewerRepository.save(interviewer);
    }

    /**
     * Update a interviewer.
     *
     * @param interviewer the entity to save.
     * @return the persisted entity.
     */
    public Interviewer update(Interviewer interviewer) {
        log.debug("Request to update Interviewer : {}", interviewer);
        return interviewerRepository.save(interviewer);
    }

    /**
     * Partially update a interviewer.
     *
     * @param interviewer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Interviewer> partialUpdate(Interviewer interviewer) {
        log.debug("Request to partially update Interviewer : {}", interviewer);

        return interviewerRepository
            .findById(interviewer.getId())
            .map(existingInterviewer -> {
                if (interviewer.getPhoto() != null) {
                    existingInterviewer.setPhoto(interviewer.getPhoto());
                }
                if (interviewer.getPhotoContentType() != null) {
                    existingInterviewer.setPhotoContentType(interviewer.getPhotoContentType());
                }

                return existingInterviewer;
            })
            .map(interviewerRepository::save);
    }

    /**
     * Get all the interviewers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Interviewer> findAll(Pageable pageable) {
        log.debug("Request to get all Interviewers");
        return interviewerRepository.findAll(pageable);
    }

    /**
     * Get one interviewer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Interviewer> findOne(Long id) {
        log.debug("Request to get Interviewer : {}", id);
        return interviewerRepository.findById(id);
    }

    /**
     * Delete the interviewer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Interviewer : {}", id);
        interviewerRepository.deleteById(id);
    }
}
