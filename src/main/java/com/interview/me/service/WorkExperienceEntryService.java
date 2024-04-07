package com.interview.me.service;

import com.interview.me.domain.WorkExperienceEntry;
import com.interview.me.repository.WorkExperienceEntryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.WorkExperienceEntry}.
 */
@Service
@Transactional
public class WorkExperienceEntryService {

    private final Logger log = LoggerFactory.getLogger(WorkExperienceEntryService.class);

    private final WorkExperienceEntryRepository workExperienceEntryRepository;

    public WorkExperienceEntryService(WorkExperienceEntryRepository workExperienceEntryRepository) {
        this.workExperienceEntryRepository = workExperienceEntryRepository;
    }

    /**
     * Save a workExperienceEntry.
     *
     * @param workExperienceEntry the entity to save.
     * @return the persisted entity.
     */
    public WorkExperienceEntry save(WorkExperienceEntry workExperienceEntry) {
        log.debug("Request to save WorkExperienceEntry : {}", workExperienceEntry);
        return workExperienceEntryRepository.save(workExperienceEntry);
    }

    /**
     * Update a workExperienceEntry.
     *
     * @param workExperienceEntry the entity to save.
     * @return the persisted entity.
     */
    public WorkExperienceEntry update(WorkExperienceEntry workExperienceEntry) {
        log.debug("Request to update WorkExperienceEntry : {}", workExperienceEntry);
        return workExperienceEntryRepository.save(workExperienceEntry);
    }

    /**
     * Partially update a workExperienceEntry.
     *
     * @param workExperienceEntry the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkExperienceEntry> partialUpdate(WorkExperienceEntry workExperienceEntry) {
        log.debug("Request to partially update WorkExperienceEntry : {}", workExperienceEntry);

        return workExperienceEntryRepository
            .findById(workExperienceEntry.getId())
            .map(existingWorkExperienceEntry -> {
                if (workExperienceEntry.getTitle() != null) {
                    existingWorkExperienceEntry.setTitle(workExperienceEntry.getTitle());
                }
                if (workExperienceEntry.getStartDate() != null) {
                    existingWorkExperienceEntry.setStartDate(workExperienceEntry.getStartDate());
                }
                if (workExperienceEntry.getEndDate() != null) {
                    existingWorkExperienceEntry.setEndDate(workExperienceEntry.getEndDate());
                }
                if (workExperienceEntry.getCompany() != null) {
                    existingWorkExperienceEntry.setCompany(workExperienceEntry.getCompany());
                }
                if (workExperienceEntry.getDescription() != null) {
                    existingWorkExperienceEntry.setDescription(workExperienceEntry.getDescription());
                }
                if (workExperienceEntry.getIndustry() != null) {
                    existingWorkExperienceEntry.setIndustry(workExperienceEntry.getIndustry());
                }
                if (workExperienceEntry.getFormattedLocation() != null) {
                    existingWorkExperienceEntry.setFormattedLocation(workExperienceEntry.getFormattedLocation());
                }
                if (workExperienceEntry.getPostalCode() != null) {
                    existingWorkExperienceEntry.setPostalCode(workExperienceEntry.getPostalCode());
                }
                if (workExperienceEntry.getRegion() != null) {
                    existingWorkExperienceEntry.setRegion(workExperienceEntry.getRegion());
                }
                if (workExperienceEntry.getCountry() != null) {
                    existingWorkExperienceEntry.setCountry(workExperienceEntry.getCountry());
                }
                if (workExperienceEntry.getCountryCode() != null) {
                    existingWorkExperienceEntry.setCountryCode(workExperienceEntry.getCountryCode());
                }
                if (workExperienceEntry.getRawInputLocation() != null) {
                    existingWorkExperienceEntry.setRawInputLocation(workExperienceEntry.getRawInputLocation());
                }
                if (workExperienceEntry.getStreet() != null) {
                    existingWorkExperienceEntry.setStreet(workExperienceEntry.getStreet());
                }
                if (workExperienceEntry.getStreetNumber() != null) {
                    existingWorkExperienceEntry.setStreetNumber(workExperienceEntry.getStreetNumber());
                }
                if (workExperienceEntry.getApartmentNumber() != null) {
                    existingWorkExperienceEntry.setApartmentNumber(workExperienceEntry.getApartmentNumber());
                }
                if (workExperienceEntry.getCity() != null) {
                    existingWorkExperienceEntry.setCity(workExperienceEntry.getCity());
                }

                return existingWorkExperienceEntry;
            })
            .map(workExperienceEntryRepository::save);
    }

    /**
     * Get all the workExperienceEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkExperienceEntry> findAll(Pageable pageable) {
        log.debug("Request to get all WorkExperienceEntries");
        return workExperienceEntryRepository.findAll(pageable);
    }

    /**
     * Get one workExperienceEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkExperienceEntry> findOne(Long id) {
        log.debug("Request to get WorkExperienceEntry : {}", id);
        return workExperienceEntryRepository.findById(id);
    }

    /**
     * Delete the workExperienceEntry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkExperienceEntry : {}", id);
        workExperienceEntryRepository.deleteById(id);
    }
}
