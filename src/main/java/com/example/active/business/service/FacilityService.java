package com.example.active.business.service;

import com.example.active.business.domain.FacilityDTO;
import com.example.active.data.entity.Facility;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FacilityService {
    Optional<FacilityDTO> findById(Long id, Double lat, Double lng);
    Optional<FacilityDTO> findByKey(String key, Double lat, Double lng);
    List<FacilityDTO> findAll(String query, String sort, Double lat, Double lng);
    Map<Long, FacilityDTO> findAllWithDistance(Sort sort, Double lat, Double lng);
    List<FacilityDTO> findByType(String query, String type, String sort, Double lat, Double lng);

    void save(Facility facility);

    void deleteFacility(Long id);
}