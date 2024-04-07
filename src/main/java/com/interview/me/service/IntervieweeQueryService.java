package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.Interviewee;
import com.interview.me.repository.IntervieweeRepository;
import com.interview.me.service.criteria.IntervieweeCriteria;
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
 * Service for executing complex queries for {@link Interviewee} entities in the database.
 * The main input is a {@link IntervieweeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Interviewee} or a {@link Page} of {@link Interviewee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntervieweeQueryService extends QueryService<Interviewee> {

    private final Logger log = LoggerFactory.getLogger(IntervieweeQueryService.class);

    private final IntervieweeRepository intervieweeRepository;

    public IntervieweeQueryService(IntervieweeRepository intervieweeRepository) {
        this.intervieweeRepository = intervieweeRepository;
    }

    /**
     * Return a {@link List} of {@link Interviewee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Interviewee> findByCriteria(IntervieweeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Interviewee> specification = createSpecification(criteria);
        return intervieweeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Interviewee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Interviewee> findByCriteria(IntervieweeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Interviewee> specification = createSpecification(criteria);
        return intervieweeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntervieweeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Interviewee> specification = createSpecification(criteria);
        return intervieweeRepository.count(specification);
    }

    /**
     * Function to convert {@link IntervieweeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Interviewee> createSpecification(IntervieweeCriteria criteria) {
        Specification<Interviewee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Interviewee_.id));
            }
            if (criteria.getInternalUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInternalUserId(),
                            root -> root.join(Interviewee_.internalUser, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getPersonalDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPersonalDetailId(),
                            root -> root.join(Interviewee_.personalDetail, JoinType.LEFT).get(PersonalDetail_.id)
                        )
                    );
            }
            if (criteria.getMembershipId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMembershipId(),
                            root -> root.join(Interviewee_.membership, JoinType.LEFT).get(MembershipLevel_.id)
                        )
                    );
            }
            if (criteria.getAppointmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAppointmentId(),
                            root -> root.join(Interviewee_.appointments, JoinType.LEFT).get(Appointment_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
