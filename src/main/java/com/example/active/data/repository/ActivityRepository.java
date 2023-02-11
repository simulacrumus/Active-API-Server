package com.example.active.data.repository;

import com.example.active.data.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByTypeId(Long typeId);
    Optional<Activity> findByTitleIgnoreCase(String title);
    Activity findByTitleContains(String title);
    List<Activity> findByTypeKey(String key);
}
