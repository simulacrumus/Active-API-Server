package com.example.active.data.repository;

import com.example.active.data.entity.FacilityReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityReservationRepository extends JpaRepository<FacilityReservation, Long> {
    Optional<FacilityReservation> findById(String id);
}
