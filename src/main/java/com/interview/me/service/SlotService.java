package com.interview.me.service;

import com.interview.me.domain.Slot;
import com.interview.me.repository.SlotRepository;
import java.util.Optional;

import com.interview.me.web.rest.SlotResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.interview.me.domain.Slot}.
 */
@Service
@Transactional
public class SlotService {

    private final Logger log = LoggerFactory.getLogger(SlotService.class);

    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    /**
     * Save a slot.
     *
     * @param slot the entity to save.
     * @return the persisted entity.
     */
    public Slot save(Slot slot) {
        log.debug("Request to save Slot : {}", slot);
        return slotRepository.save(slot);
    }

    /**
     * Update a slot.
     *
     * @param slot the entity to save.
     * @return the persisted entity.
     */
    public Slot update(Slot slot) {
        log.debug("Request to update Slot : {}", slot);
        return slotRepository.save(slot);
    }

    /**
     * Partially update a slot.
     *
     * @param slot the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Slot> partialUpdate(Slot slot) {
        log.debug("Request to partially update Slot : {}", slot);

        return slotRepository
            .findById(slot.getId())
            .map(existingSlot -> {
                if (slot.getIsAvailable() != null) {
                    existingSlot.setIsAvailable(slot.getIsAvailable());
                }

                return existingSlot;
            })
            .map(slotRepository::save);
    }

    /**
     * Get all the slots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Slot> findAll(Pageable pageable) {
        log.debug("Request to get all Slots");
        return slotRepository.findAll(pageable);
    }

    /**
     * Get one slot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Slot> findOne(Long id) {
        log.debug("Request to get Slot : {}", id);
        return slotRepository.findById(id);
    }

    /**
     * Delete the slot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Slot : {}", id);
        slotRepository.deleteById(id);
    }

    public Slot saveX(SlotResource.SlotX slot) {

        return null;
    }
}
