package com.interview.me.repository;

import com.interview.me.domain.MembershipLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MembershipLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipLevelRepository extends JpaRepository<MembershipLevel, Long> {}
