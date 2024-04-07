package com.interview.me.service;

import com.interview.me.domain.*; // for static metamodels
import com.interview.me.domain.InterviewNote;
import com.interview.me.repository.InterviewNoteRepository;
import com.interview.me.service.criteria.InterviewNoteCriteria;
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
 * Service for executing complex queries for {@link InterviewNote} entities in the database.
 * The main input is a {@link InterviewNoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InterviewNote} or a {@link Page} of {@link InterviewNote} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterviewNoteQueryService extends QueryService<InterviewNote> {

    private final Logger log = LoggerFactory.getLogger(InterviewNoteQueryService.class);

    private final InterviewNoteRepository interviewNoteRepository;

    public InterviewNoteQueryService(InterviewNoteRepository interviewNoteRepository) {
        this.interviewNoteRepository = interviewNoteRepository;
    }

    /**
     * Return a {@link List} of {@link InterviewNote} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InterviewNote> findByCriteria(InterviewNoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InterviewNote> specification = createSpecification(criteria);
        return interviewNoteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InterviewNote} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InterviewNote> findByCriteria(InterviewNoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InterviewNote> specification = createSpecification(criteria);
        return interviewNoteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterviewNoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InterviewNote> specification = createSpecification(criteria);
        return interviewNoteRepository.count(specification);
    }

    /**
     * Function to convert {@link InterviewNoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InterviewNote> createSpecification(InterviewNoteCriteria criteria) {
        Specification<InterviewNote> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InterviewNote_.id));
            }
            if (criteria.getNoteText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoteText(), InterviewNote_.noteText));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), InterviewNote_.rating));
            }
            if (criteria.getActionItems() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionItems(), InterviewNote_.actionItems));
            }
            if (criteria.getFeedback() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeedback(), InterviewNote_.feedback));
            }
        }
        return specification;
    }
}
