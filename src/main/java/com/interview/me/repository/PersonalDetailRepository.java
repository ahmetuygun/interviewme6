package com.interview.me.repository;

import com.interview.me.domain.PersonalDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonalDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalDetailRepository extends JpaRepository<PersonalDetail, Long>, JpaSpecificationExecutor<PersonalDetail> {}
