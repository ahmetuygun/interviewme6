package com.interview.me.service;

import com.interview.me.domain.MembershipLevel;
import com.interview.me.repository.MembershipLevelRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.MembershipLevel}.
 */
@Service
@Transactional
public class MembershipLevelService {

    private final Logger log = LoggerFactory.getLogger(MembershipLevelService.class);

    private final MembershipLevelRepository membershipLevelRepository;

    public MembershipLevelService(MembershipLevelRepository membershipLevelRepository) {
        this.membershipLevelRepository = membershipLevelRepository;
    }

    /**
     * Save a membershipLevel.
     *
     * @param membershipLevel the entity to save.
     * @return the persisted entity.
     */
    public MembershipLevel save(MembershipLevel membershipLevel) {
        log.debug("Request to save MembershipLevel : {}", membershipLevel);
        return membershipLevelRepository.save(membershipLevel);
    }

    /**
     * Update a membershipLevel.
     *
     * @param membershipLevel the entity to save.
     * @return the persisted entity.
     */
    public MembershipLevel update(MembershipLevel membershipLevel) {
        log.debug("Request to update MembershipLevel : {}", membershipLevel);
        return membershipLevelRepository.save(membershipLevel);
    }

    /**
     * Partially update a membershipLevel.
     *
     * @param membershipLevel the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MembershipLevel> partialUpdate(MembershipLevel membershipLevel) {
        log.debug("Request to partially update MembershipLevel : {}", membershipLevel);

        return membershipLevelRepository
            .findById(membershipLevel.getId())
            .map(existingMembershipLevel -> {
                if (membershipLevel.getName() != null) {
                    existingMembershipLevel.setName(membershipLevel.getName());
                }
                if (membershipLevel.getDescription() != null) {
                    existingMembershipLevel.setDescription(membershipLevel.getDescription());
                }
                if (membershipLevel.getSessionAmount() != null) {
                    existingMembershipLevel.setSessionAmount(membershipLevel.getSessionAmount());
                }
                if (membershipLevel.getPrice() != null) {
                    existingMembershipLevel.setPrice(membershipLevel.getPrice());
                }

                return existingMembershipLevel;
            })
            .map(membershipLevelRepository::save);
    }

    /**
     * Get all the membershipLevels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MembershipLevel> findAll() {
        log.debug("Request to get all MembershipLevels");
        return membershipLevelRepository.findAll();
    }

    /**
     * Get one membershipLevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MembershipLevel> findOne(Long id) {
        log.debug("Request to get MembershipLevel : {}", id);
        return membershipLevelRepository.findById(id);
    }

    /**
     * Delete the membershipLevel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MembershipLevel : {}", id);
        membershipLevelRepository.deleteById(id);
    }
}
