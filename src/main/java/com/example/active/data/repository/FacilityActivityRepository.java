package com.example.active.data.repository;

import com.example.active.data.entity.FacilityActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityActivityRepository extends JpaRepository<FacilityActivity, Long> {
    List<FacilityActivity> findByFacilityActivityIdFacilityId(Long id);

    List<FacilityActivity> findByFacilityActivityIdActivityId(Long id);

    List<FacilityActivity> findAllByFacilityActivityIdActivityIdIn(List<Long> activityIds);
}
