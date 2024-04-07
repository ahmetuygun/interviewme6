package com.interview.me.repository;

import com.interview.me.domain.WorkExperienceEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkExperienceEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkExperienceEntryRepository
    extends JpaRepository<WorkExperienceEntry, Long>, JpaSpecificationExecutor<WorkExperienceEntry> {}
