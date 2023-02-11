package com.example.active.data.repository;

import com.example.active.data.entity.Facility;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long>  {
    List<Facility> findByTitleContains(String title, Sort sort);
    List<Facility> findByTitleContains(String title);
    Optional<Facility> findByKey(String key);
}