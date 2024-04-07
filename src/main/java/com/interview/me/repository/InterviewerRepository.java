package com.interview.me.repository;

import com.interview.me.domain.Interviewer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Interviewer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long>, JpaSpecificationExecutor<Interviewer> {}
