package com.example.active.business.service;

import com.example.active.business.domain.ActivityDTO;
import com.example.active.business.domain.AvailableActivityDTO;
import com.example.active.data.entity.Activity;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface ActivityService{
    Optional<ActivityDTO> findById(Long id, Double lat, Double lng);

    Page<ActivityDTO> findAll(String query, Optional<Boolean> isAvailable, Integer page, Integer size, String sort, Double lat, Double lng);

    Page<ActivityDTO> findByCategory(
            String category, String query, Optional<Boolean> isAvailable, Integer page, Integer size, String sort, Double lat, Double lng);

    Page<ActivityDTO> findByCategoryAndType(
            String query,
            Optional<Boolean> isAvailable,
            String category,
            String type, Integer page, Integer size, String sort, Double lat, Double lng);

    Page<ActivityDTO> findByType(
            String query,
            Optional<Boolean> isAvailable,
            String type,
            Integer page,
            Integer size,
            String sort,
            Double lat,
            Double lng);

    Page<ActivityDTO> findByFacility(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            Integer page,
            Integer size,
            String sort,
            Double lat,
            Double lng
    );

    Page<ActivityDTO> findByFacilityAndCategory(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            String category,
            Integer page,
            Integer size,
            String sort,
            Double lat,
            Double lng
    );

    Page<ActivityDTO> findByFacilityAndCategoryAndType(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            String category,
            String type,
            Integer page,
            Integer size,
            String sort,
            Double lat,
            Double lng
    );

    Page<ActivityDTO> findByFacilityAndType(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            String type,
            Integer page,
            Integer size,
            String sort,
            Double lat,
            Double lng
    );

    void saveAvailableActivities(List<AvailableActivityDTO> availableActivityDTOS);

    void saveAvailableActivity(AvailableActivityDTO availableActivityDTO);

    void saveActivity(Activity activity);

    void deleteActivity(Long id);
}
