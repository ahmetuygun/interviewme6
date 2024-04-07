package com.interview.me.service;

import com.interview.me.domain.EducationEntry;
import com.interview.me.repository.EducationEntryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.EducationEntry}.
 */
@Service
@Transactional
public class EducationEntryService {

    private final Logger log = LoggerFactory.getLogger(EducationEntryService.class);

    private final EducationEntryRepository educationEntryRepository;

    public EducationEntryService(EducationEntryRepository educationEntryRepository) {
        this.educationEntryRepository = educationEntryRepository;
    }

    /**
     * Save a educationEntry.
     *
     * @param educationEntry the entity to save.
     * @return the persisted entity.
     */
    public EducationEntry save(EducationEntry educationEntry) {
        log.debug("Request to save EducationEntry : {}", educationEntry);
        return educationEntryRepository.save(educationEntry);
    }

    /**
     * Update a educationEntry.
     *
     * @param educationEntry the entity to save.
     * @return the persisted entity.
     */
    public EducationEntry update(EducationEntry educationEntry) {
        log.debug("Request to update EducationEntry : {}", educationEntry);
        return educationEntryRepository.save(educationEntry);
    }

    /**
     * Partially update a educationEntry.
     *
     * @param educationEntry the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EducationEntry> partialUpdate(EducationEntry educationEntry) {
        log.debug("Request to partially update EducationEntry : {}", educationEntry);

        return educationEntryRepository
            .findById(educationEntry.getId())
            .map(existingEducationEntry -> {
                if (educationEntry.getTitle() != null) {
                    existingEducationEntry.setTitle(educationEntry.getTitle());
                }
                if (educationEntry.getStartDate() != null) {
                    existingEducationEntry.setStartDate(educationEntry.getStartDate());
                }
                if (educationEntry.getEndDate() != null) {
                    existingEducationEntry.setEndDate(educationEntry.getEndDate());
                }
                if (educationEntry.getEstablishment() != null) {
                    existingEducationEntry.setEstablishment(educationEntry.getEstablishment());
                }
                if (educationEntry.getDescription() != null) {
                    existingEducationEntry.setDescription(educationEntry.getDescription());
                }
                if (educationEntry.getGpa() != null) {
                    existingEducationEntry.setGpa(educationEntry.getGpa());
                }
                if (educationEntry.getAccreditation() != null) {
                    existingEducationEntry.setAccreditation(educationEntry.getAccreditation());
                }

                return existingEducationEntry;
            })
            .map(educationEntryRepository::save);
    }

    /**
     * Get all the educationEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationEntry> findAll(Pageable pageable) {
        log.debug("Request to get all EducationEntries");
        return educationEntryRepository.findAll(pageable);
    }

    /**
     * Get one educationEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EducationEntry> findOne(Long id) {
        log.debug("Request to get EducationEntry : {}", id);
        return educationEntryRepository.findById(id);
    }

    /**
     * Delete the educationEntry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EducationEntry : {}", id);
        educationEntryRepository.deleteById(id);
    }
}
