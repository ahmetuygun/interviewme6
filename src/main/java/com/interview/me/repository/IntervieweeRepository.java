package com.interview.me.repository;

import com.interview.me.domain.Interviewee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Interviewee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervieweeRepository extends JpaRepository<Interviewee, Long>, JpaSpecificationExecutor<Interviewee> {}
