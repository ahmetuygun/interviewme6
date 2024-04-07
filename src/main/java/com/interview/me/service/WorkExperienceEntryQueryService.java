package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.WorkExperienceEntry;
import com.interview.me.repository.WorkExperienceEntryRepository;
import com.interview.me.service.criteria.WorkExperienceEntryCriteria;
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
 * Service for executing complex queries for {@link WorkExperienceEntry} entities in the database.
 * The main input is a {@link WorkExperienceEntryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkExperienceEntry} or a {@link Page} of {@link WorkExperienceEntry} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkExperienceEntryQueryService extends QueryService<WorkExperienceEntry> {

    private final Logger log = LoggerFactory.getLogger(WorkExperienceEntryQueryService.class);

    private final WorkExperienceEntryRepository workExperienceEntryRepository;

    public WorkExperienceEntryQueryService(WorkExperienceEntryRepository workExperienceEntryRepository) {
        this.workExperienceEntryRepository = workExperienceEntryRepository;
    }

    /**
     * Return a {@link List} of {@link WorkExperienceEntry} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkExperienceEntry> findByCriteria(WorkExperienceEntryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkExperienceEntry> specification = createSpecification(criteria);
        return workExperienceEntryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkExperienceEntry} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkExperienceEntry> findByCriteria(WorkExperienceEntryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkExperienceEntry> specification = createSpecification(criteria);
        return workExperienceEntryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkExperienceEntryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkExperienceEntry> specification = createSpecification(criteria);
        return workExperienceEntryRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkExperienceEntryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkExperienceEntry> createSpecification(WorkExperienceEntryCriteria criteria) {
        Specification<WorkExperienceEntry> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkExperienceEntry_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), WorkExperienceEntry_.title));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), WorkExperienceEntry_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), WorkExperienceEntry_.endDate));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompany(), WorkExperienceEntry_.company));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), WorkExperienceEntry_.description));
            }
            if (criteria.getIndustry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndustry(), WorkExperienceEntry_.industry));
            }
            if (criteria.getFormattedLocation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFormattedLocation(), WorkExperienceEntry_.formattedLocation));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), WorkExperienceEntry_.postalCode));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), WorkExperienceEntry_.region));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), WorkExperienceEntry_.country));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), WorkExperienceEntry_.countryCode));
            }
            if (criteria.getRawInputLocation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRawInputLocation(), WorkExperienceEntry_.rawInputLocation));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), WorkExperienceEntry_.street));
            }
            if (criteria.getStreetNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetNumber(), WorkExperienceEntry_.streetNumber));
            }
            if (criteria.getApartmentNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getApartmentNumber(), WorkExperienceEntry_.apartmentNumber));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), WorkExperienceEntry_.city));
            }
            if (criteria.getPersonalDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPersonalDetailId(),
                            root -> root.join(WorkExperienceEntry_.personalDetail, JoinType.LEFT).get(PersonalDetail_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
