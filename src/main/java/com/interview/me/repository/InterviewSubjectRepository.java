package com.interview.me.repository;

import com.interview.me.domain.InterviewSubject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InterviewSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewSubjectRepository extends JpaRepository<InterviewSubject, Long>, JpaSpecificationExecutor<InterviewSubject> {}
