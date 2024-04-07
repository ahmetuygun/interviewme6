package com.interview.me.service;

import com.interview.me.domain.PersonalDetail;
import com.interview.me.repository.PersonalDetailRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.PersonalDetail}.
 */
@Service
@Transactional
public class PersonalDetailService {

    private final Logger log = LoggerFactory.getLogger(PersonalDetailService.class);

    private final PersonalDetailRepository personalDetailRepository;

    public PersonalDetailService(PersonalDetailRepository personalDetailRepository) {
        this.personalDetailRepository = personalDetailRepository;
    }

    /**
     * Save a personalDetail.
     *
     * @param personalDetail the entity to save.
     * @return the persisted entity.
     */
    public PersonalDetail save(PersonalDetail personalDetail) {
        log.debug("Request to save PersonalDetail : {}", personalDetail);
        return personalDetailRepository.save(personalDetail);
    }

    /**
     * Update a personalDetail.
     *
     * @param personalDetail the entity to save.
     * @return the persisted entity.
     */
    public PersonalDetail update(PersonalDetail personalDetail) {
        log.debug("Request to update PersonalDetail : {}", personalDetail);
        return personalDetailRepository.save(personalDetail);
    }

    /**
     * Partially update a personalDetail.
     *
     * @param personalDetail the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonalDetail> partialUpdate(PersonalDetail personalDetail) {
        log.debug("Request to partially update PersonalDetail : {}", personalDetail);

        return personalDetailRepository
            .findById(personalDetail.getId())
            .map(existingPersonalDetail -> {

                if (personalDetail.getCountry() != null) {
                    existingPersonalDetail.setCountry(personalDetail.getCountry());
                }

                if (personalDetail.getCity() != null) {
                    existingPersonalDetail.setCity(personalDetail.getCity());
                }
                if (personalDetail.getSelfSummary() != null) {
                    existingPersonalDetail.setSelfSummary(personalDetail.getSelfSummary());
                }

                if (personalDetail.getDateOfBirth() != null) {
                    existingPersonalDetail.setDateOfBirth(personalDetail.getDateOfBirth());
                }
                if (personalDetail.getPlaceOfBirth() != null) {
                    existingPersonalDetail.setPlaceOfBirth(personalDetail.getPlaceOfBirth());
                }
                if (personalDetail.getPhones() != null) {
                    existingPersonalDetail.setPhones(personalDetail.getPhones());
                }
                if (personalDetail.getMails() != null) {
                    existingPersonalDetail.setMails(personalDetail.getMails());
                }
                if (personalDetail.getUrls() != null) {
                    existingPersonalDetail.setUrls(personalDetail.getUrls());
                }
                if (personalDetail.getCurrentProfession() != null) {
                    existingPersonalDetail.setCurrentProfession(personalDetail.getCurrentProfession());
                }
                if (personalDetail.getGender() != null) {
                    existingPersonalDetail.setGender(personalDetail.getGender());
                }

                if (personalDetail.getCurrentSalary() != null) {
                    existingPersonalDetail.setCurrentSalary(personalDetail.getCurrentSalary());
                }

                return existingPersonalDetail;
            })
            .map(personalDetailRepository::save);
    }

    /**
     * Get all the personalDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalDetail> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalDetails");
        return personalDetailRepository.findAll(pageable);
    }

    /**
     *  Get all the personalDetails where Interviewer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalDetail> findAllWhereInterviewerIsNull() {
        log.debug("Request to get all personalDetails where Interviewer is null");
        return StreamSupport
            .stream(personalDetailRepository.findAll().spliterator(), false)
            .filter(personalDetail -> personalDetail.getInterviewer() == null)
            .toList();
    }

    /**
     *  Get all the personalDetails where Interviewee is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalDetail> findAllWhereIntervieweeIsNull() {
        log.debug("Request to get all personalDetails where Interviewee is null");
        return StreamSupport
            .stream(personalDetailRepository.findAll().spliterator(), false)
            .filter(personalDetail -> personalDetail.getInterviewee() == null)
            .toList();
    }

    /**
     * Get one personalDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalDetail> findOne(Long id) {
        log.debug("Request to get PersonalDetail : {}", id);
        return personalDetailRepository.findById(id);
    }

    /**
     * Delete the personalDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalDetail : {}", id);
        personalDetailRepository.deleteById(id);
    }
}
