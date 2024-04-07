package com.interview.me.service;

import com.interview.me.domain.InterviewNote;
import com.interview.me.repository.InterviewNoteRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.InterviewNote}.
 */
@Service
@Transactional
public class InterviewNoteService {

    private final Logger log = LoggerFactory.getLogger(InterviewNoteService.class);

    private final InterviewNoteRepository interviewNoteRepository;

    public InterviewNoteService(InterviewNoteRepository interviewNoteRepository) {
        this.interviewNoteRepository = interviewNoteRepository;
    }

    /**
     * Save a interviewNote.
     *
     * @param interviewNote the entity to save.
     * @return the persisted entity.
     */
    public InterviewNote save(InterviewNote interviewNote) {
        log.debug("Request to save InterviewNote : {}", interviewNote);
        return interviewNoteRepository.save(interviewNote);
    }

    /**
     * Update a interviewNote.
     *
     * @param interviewNote the entity to save.
     * @return the persisted entity.
     */
    public InterviewNote update(InterviewNote interviewNote) {
        log.debug("Request to update InterviewNote : {}", interviewNote);
        return interviewNoteRepository.save(interviewNote);
    }

    /**
     * Partially update a interviewNote.
     *
     * @param interviewNote the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InterviewNote> partialUpdate(InterviewNote interviewNote) {
        log.debug("Request to partially update InterviewNote : {}", interviewNote);

        return interviewNoteRepository
            .findById(interviewNote.getId())
            .map(existingInterviewNote -> {
                if (interviewNote.getNoteText() != null) {
                    existingInterviewNote.setNoteText(interviewNote.getNoteText());
                }
                if (interviewNote.getRating() != null) {
                    existingInterviewNote.setRating(interviewNote.getRating());
                }
                if (interviewNote.getActionItems() != null) {
                    existingInterviewNote.setActionItems(interviewNote.getActionItems());
                }
                if (interviewNote.getFeedback() != null) {
                    existingInterviewNote.setFeedback(interviewNote.getFeedback());
                }

                return existingInterviewNote;
            })
            .map(interviewNoteRepository::save);
    }

    /**
     * Get all the interviewNotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InterviewNote> findAll(Pageable pageable) {
        log.debug("Request to get all InterviewNotes");
        return interviewNoteRepository.findAll(pageable);
    }

    /**
     * Get one interviewNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InterviewNote> findOne(Long id) {
        log.debug("Request to get InterviewNote : {}", id);
        return interviewNoteRepository.findById(id);
    }

    /**
     * Delete the interviewNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InterviewNote : {}", id);
        interviewNoteRepository.deleteById(id);
    }
}
