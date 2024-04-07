package com.interview.me.repository;

import com.interview.me.domain.InterviewNote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InterviewNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewNoteRepository extends JpaRepository<InterviewNote, Long>, JpaSpecificationExecutor<InterviewNote> {}
