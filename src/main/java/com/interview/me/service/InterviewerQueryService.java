package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.Interviewer;
import com.interview.me.repository.InterviewerRepository;
import com.interview.me.service.criteria.InterviewerCriteria;
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
 * Service for executing complex queries for {@link Interviewer} entities in the database.
 * The main input is a {@link InterviewerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Interviewer} or a {@link Page} of {@link Interviewer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterviewerQueryService extends QueryService<Interviewer> {

    private final Logger log = LoggerFactory.getLogger(InterviewerQueryService.class);

    private final InterviewerRepository interviewerRepository;

    public InterviewerQueryService(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    /**
     * Return a {@link List} of {@link Interviewer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Interviewer> findByCriteria(InterviewerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Interviewer> specification = createSpecification(criteria);
        return interviewerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Interviewer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Interviewer> findByCriteria(InterviewerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Interviewer> specification = createSpecification(criteria);
        return interviewerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterviewerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Interviewer> specification = createSpecification(criteria);
        return interviewerRepository.count(specification);
    }

    /**
     * Function to convert {@link InterviewerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Interviewer> createSpecification(InterviewerCriteria criteria) {
        Specification<Interviewer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Interviewer_.id));
            }
            if (criteria.getInternalUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInternalUserId(),
                            root -> root.join(Interviewer_.internalUser, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getPersonalDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPersonalDetailId(),
                            root -> root.join(Interviewer_.personalDetail, JoinType.LEFT).get(PersonalDetail_.id)
                        )
                    );
            }
            if (criteria.getSubjectsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubjectsId(),
                            root -> root.join(Interviewer_.subjects, JoinType.LEFT).get(InterviewSubject_.id)
                        )
                    );
            }
            if (criteria.getSlotsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSlotsId(), root -> root.join(Interviewer_.slots, JoinType.LEFT).get(Slot_.id))
                    );
            }
            if (criteria.getAppointmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAppointmentId(),
                            root -> root.join(Interviewer_.appointments, JoinType.LEFT).get(Appointment_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
