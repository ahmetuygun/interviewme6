package com.interview.me.repository;

import com.interview.me.domain.Slot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Slot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long>, JpaSpecificationExecutor<Slot> {
    @Query(value = "SELECT hour FROM appointment_hours WHERE slot_id = ?1", nativeQuery = true)
    List<String> findHoursBySlotId(Long slotId);

    Optional<Slot> findByInterviewer_IdAndYearAndMonthAndDay(Long interviewId, int year, int month, int day);

    List<Slot> findByInterviewer_IdAndYearAndMonthAndDayIsGreaterThanEqual(Long interviewId, int year, int month, int day);

}
