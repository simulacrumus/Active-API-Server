package com.example.active.data.repository;

import com.example.active.data.entity.AvailableActivity;
import com.example.active.data.repository.custom.CustomAvailableActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailableActivityRepository extends JpaRepository<AvailableActivity, Long>, CustomAvailableActivityRepository {
    Page<AvailableActivity> findByActivityTitleContainsAndTimeAfter(Pageable pageable, String title, LocalDateTime time);
    Page<AvailableActivity> findByActivityTitleContainsAndIsAvailableAndTimeAfter(Pageable pageable, String title, Boolean available, LocalDateTime time);
    Page<AvailableActivity> findByActivityTypeCategoryKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable, String title, String key, LocalDateTime time);
    Page<AvailableActivity> findByActivityTypeCategoryKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable, String title, String key, Boolean available, LocalDateTime time);
    Page<AvailableActivity> findAvailableActivitiesByActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable,
            String activityTypeCategoryKey,
            String activityTypeKey,
            String activityTitle,
            LocalDateTime time
    );
    Page<AvailableActivity> findAvailableActivitiesByActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable,
            String activityTypeCategoryKey,
            String activityTypeKey,
            String activityTitle,
            Boolean available,
            LocalDateTime time
    );
    List<AvailableActivity> findByActivityTypeKey(String key);
    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTitle,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTitle,
            Boolean available,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTypeCategoryKey,
            String activityTitle,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTypeCategoryKey,
            String activityTitle,
            Boolean available,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTypeCategoryKey,
            String activityTypeKey,
            String activityTitle,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTypeCategoryKey,
            String activityTypeKey,
            String activityTitle,
            Boolean available,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTypeKey,
            String activityTitle,
            LocalDateTime time
    );

    Page<AvailableActivity>
    findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable,
            String facilityReservationFacilityKey,
            String activityTypeKey,
            String activityTitle,
            Boolean available,
            LocalDateTime time
    );

    Page<AvailableActivity> findAvailableActivitiesByActivityTypeKeyAndActivityTitleContainsAndTimeAfter(
            Pageable pageable,
            String activityType,
            String activityTitle,
            LocalDateTime time
    );

    Page<AvailableActivity> findAvailableActivitiesByActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
            Pageable pageable,
            String activityType,
            String activityTitle,
            Boolean available,
            LocalDateTime time
    );
}
