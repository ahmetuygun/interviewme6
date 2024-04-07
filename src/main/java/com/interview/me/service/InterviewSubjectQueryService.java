package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.InterviewSubject;
import com.interview.me.repository.InterviewSubjectRepository;
import com.interview.me.service.criteria.InterviewSubjectCriteria;
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
 * Service for executing complex queries for {@link InterviewSubject} entities in the database.
 * The main input is a {@link InterviewSubjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InterviewSubject} or a {@link Page} of {@link InterviewSubject} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterviewSubjectQueryService extends QueryService<InterviewSubject> {

    private final Logger log = LoggerFactory.getLogger(InterviewSubjectQueryService.class);

    private final InterviewSubjectRepository interviewSubjectRepository;

    public InterviewSubjectQueryService(InterviewSubjectRepository interviewSubjectRepository) {
        this.interviewSubjectRepository = interviewSubjectRepository;
    }

    /**
     * Return a {@link List} of {@link InterviewSubject} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InterviewSubject> findByCriteria(InterviewSubjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InterviewSubject> specification = createSpecification(criteria);
        return interviewSubjectRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InterviewSubject} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InterviewSubject> findByCriteria(InterviewSubjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InterviewSubject> specification = createSpecification(criteria);
        return interviewSubjectRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterviewSubjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InterviewSubject> specification = createSpecification(criteria);
        return interviewSubjectRepository.count(specification);
    }

    /**
     * Function to convert {@link InterviewSubjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InterviewSubject> createSpecification(InterviewSubjectCriteria criteria) {
        Specification<InterviewSubject> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InterviewSubject_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InterviewSubject_.name));
            }
            if (criteria.getParent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParent(), InterviewSubject_.parent));
            }
            if (criteria.getInterviewerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewerId(),
                            root -> root.join(InterviewSubject_.interviewer, JoinType.LEFT).get(Interviewer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
