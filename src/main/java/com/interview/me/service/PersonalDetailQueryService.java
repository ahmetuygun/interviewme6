package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.PersonalDetail;
import com.interview.me.repository.PersonalDetailRepository;
import com.interview.me.service.criteria.PersonalDetailCriteria;
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
 * Service for executing complex queries for {@link PersonalDetail} entities in the database.
 * The main input is a {@link PersonalDetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonalDetail} or a {@link Page} of {@link PersonalDetail} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonalDetailQueryService extends QueryService<PersonalDetail> {

    private final Logger log = LoggerFactory.getLogger(PersonalDetailQueryService.class);

    private final PersonalDetailRepository personalDetailRepository;

    public PersonalDetailQueryService(PersonalDetailRepository personalDetailRepository) {
        this.personalDetailRepository = personalDetailRepository;
    }

    /**
     * Return a {@link List} of {@link PersonalDetail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalDetail> findByCriteria(PersonalDetailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonalDetail> specification = createSpecification(criteria);
        return personalDetailRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PersonalDetail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalDetail> findByCriteria(PersonalDetailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonalDetail> specification = createSpecification(criteria);
        return personalDetailRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonalDetailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonalDetail> specification = createSpecification(criteria);
        return personalDetailRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonalDetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PersonalDetail> createSpecification(PersonalDetailCriteria criteria) {
        Specification<PersonalDetail> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PersonalDetail_.id));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), PersonalDetail_.dateOfBirth));
            }
            if (criteria.getPlaceOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlaceOfBirth(), PersonalDetail_.placeOfBirth));
            }
            if (criteria.getPhones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhones(), PersonalDetail_.phones));
            }
            if (criteria.getMails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMails(), PersonalDetail_.mails));
            }
            if (criteria.getUrls() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrls(), PersonalDetail_.urls));
            }
            if (criteria.getCurrentProfession() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCurrentProfession(), PersonalDetail_.currentProfession));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), PersonalDetail_.gender));
            }
            if (criteria.getCurrentSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentSalary(), PersonalDetail_.currentSalary));
            }
            if (criteria.getWorkId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorkId(),
                            root -> root.join(PersonalDetail_.works, JoinType.LEFT).get(WorkExperienceEntry_.id)
                        )
                    );
            }
            if (criteria.getEducationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEducationId(),
                            root -> root.join(PersonalDetail_.educations, JoinType.LEFT).get(EducationEntry_.id)
                        )
                    );
            }

            if (criteria.getInterviewerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewerId(),
                            root -> root.join(PersonalDetail_.interviewer, JoinType.LEFT).get(Interviewer_.id)
                        )
                    );
            }
            if (criteria.getIntervieweeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIntervieweeId(),
                            root -> root.join(PersonalDetail_.interviewee, JoinType.LEFT).get(Interviewee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
