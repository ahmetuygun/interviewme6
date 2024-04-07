package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.Slot;
import com.interview.me.repository.SlotRepository;
import com.interview.me.service.criteria.SlotCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Slot} entities in the database.
 * The main input is a {@link SlotCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Slot} or a {@link Page} of {@link Slot} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SlotQueryService extends QueryService<Slot> {

    private final Logger log = LoggerFactory.getLogger(SlotQueryService.class);

    private final SlotRepository slotRepository;

    public SlotQueryService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    /**
     * Return a {@link List} of {@link Slot} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Slot> findByCriteria(SlotCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Slot> specification = createSpecification(criteria);
        return slotRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Slot} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Slot> findByCriteria(SlotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Slot> specification = createSpecification(criteria);
        Page<Slot> slotList = slotRepository.findAll(specification, page);
        slotList.forEach( item -> item.setAvailableHours(getAvailableHours(item)));
        return slotRepository.findAll(specification, page);
    }
    private List<String> getAvailableHours(Slot item) {
        return slotRepository.findHoursBySlotId(item.getId());
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SlotCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Slot> specification = createSpecification(criteria);
        return slotRepository.count(specification);
    }

    /**
     * Function to convert {@link SlotCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Slot> createSpecification(SlotCriteria criteria) {
        Specification<Slot> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Slot_.id));
            }
            if (criteria.getIsAvailable() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAvailable(), Slot_.isAvailable));
            }
            if (criteria.getInterviewerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewerId(),
                            root -> root.join(Slot_.interviewer, JoinType.LEFT).get(Interviewer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
