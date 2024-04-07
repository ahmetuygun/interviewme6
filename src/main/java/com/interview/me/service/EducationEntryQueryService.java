package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.EducationEntry;
import com.interview.me.repository.EducationEntryRepository;
import com.interview.me.service.criteria.EducationEntryCriteria;
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
 * Service for executing complex queries for {@link EducationEntry} entities in the database.
 * The main input is a {@link EducationEntryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EducationEntry} or a {@link Page} of {@link EducationEntry} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EducationEntryQueryService extends QueryService<EducationEntry> {

    private final Logger log = LoggerFactory.getLogger(EducationEntryQueryService.class);

    private final EducationEntryRepository educationEntryRepository;

    public EducationEntryQueryService(EducationEntryRepository educationEntryRepository) {
        this.educationEntryRepository = educationEntryRepository;
    }

    /**
     * Return a {@link List} of {@link EducationEntry} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EducationEntry> findByCriteria(EducationEntryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EducationEntry> specification = createSpecification(criteria);
        return educationEntryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EducationEntry} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationEntry> findByCriteria(EducationEntryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EducationEntry> specification = createSpecification(criteria);
        return educationEntryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EducationEntryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EducationEntry> specification = createSpecification(criteria);
        return educationEntryRepository.count(specification);
    }

    /**
     * Function to convert {@link EducationEntryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EducationEntry> createSpecification(EducationEntryCriteria criteria) {
        Specification<EducationEntry> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EducationEntry_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), EducationEntry_.title));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), EducationEntry_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), EducationEntry_.endDate));
            }
            if (criteria.getEstablishment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstablishment(), EducationEntry_.establishment));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), EducationEntry_.description));
            }
            if (criteria.getGpa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGpa(), EducationEntry_.gpa));
            }
            if (criteria.getAccreditation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccreditation(), EducationEntry_.accreditation));
            }
            if (criteria.getPersonalDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPersonalDetailId(),
                            root -> root.join(EducationEntry_.personalDetail, JoinType.LEFT).get(PersonalDetail_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
