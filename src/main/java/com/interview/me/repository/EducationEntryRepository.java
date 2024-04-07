package com.interview.me.repository;

import com.interview.me.domain.EducationEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EducationEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationEntryRepository extends JpaRepository<EducationEntry, Long>, JpaSpecificationExecutor<EducationEntry> {}
