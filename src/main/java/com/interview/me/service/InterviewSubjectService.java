package com.interview.me.service;

import com.interview.me.domain.InterviewSubject;
import com.interview.me.repository.InterviewSubjectRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.InterviewSubject}.
 */
@Service
@Transactional
public class InterviewSubjectService {

    private final Logger log = LoggerFactory.getLogger(InterviewSubjectService.class);

    private final InterviewSubjectRepository interviewSubjectRepository;

    public InterviewSubjectService(InterviewSubjectRepository interviewSubjectRepository) {
        this.interviewSubjectRepository = interviewSubjectRepository;
    }

    /**
     * Save a interviewSubject.
     *
     * @param interviewSubject the entity to save.
     * @return the persisted entity.
     */
    public InterviewSubject save(InterviewSubject interviewSubject) {
        log.debug("Request to save InterviewSubject : {}", interviewSubject);
        return interviewSubjectRepository.save(interviewSubject);
    }

    /**
     * Update a interviewSubject.
     *
     * @param interviewSubject the entity to save.
     * @return the persisted entity.
     */
    public InterviewSubject update(InterviewSubject interviewSubject) {
        log.debug("Request to update InterviewSubject : {}", interviewSubject);
        return interviewSubjectRepository.save(interviewSubject);
    }

    /**
     * Partially update a interviewSubject.
     *
     * @param interviewSubject the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InterviewSubject> partialUpdate(InterviewSubject interviewSubject) {
        log.debug("Request to partially update InterviewSubject : {}", interviewSubject);

        return interviewSubjectRepository
            .findById(interviewSubject.getId())
            .map(existingInterviewSubject -> {
                if (interviewSubject.getName() != null) {
                    existingInterviewSubject.setName(interviewSubject.getName());
                }
                if (interviewSubject.getParent() != null) {
                    existingInterviewSubject.setParent(interviewSubject.getParent());
                }

                return existingInterviewSubject;
            })
            .map(interviewSubjectRepository::save);
    }

    /**
     * Get all the interviewSubjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InterviewSubject> findAll(Pageable pageable) {
        log.debug("Request to get all InterviewSubjects");
        return interviewSubjectRepository.findAll(pageable);
    }

    /**
     * Get one interviewSubject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InterviewSubject> findOne(Long id) {
        log.debug("Request to get InterviewSubject : {}", id);
        return interviewSubjectRepository.findById(id);
    }

    /**
     * Delete the interviewSubject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InterviewSubject : {}", id);
        interviewSubjectRepository.deleteById(id);
    }
}
