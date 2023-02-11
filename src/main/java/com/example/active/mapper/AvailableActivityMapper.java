package com.example.active.mapper;

import com.example.active.business.domain.AvailableActivityDTO;
import com.example.active.data.entity.AvailableActivity;
import com.example.active.data.repository.ActivityRepository;
import com.example.active.data.repository.FacilityReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvailableActivityMapper {
    @Autowired
    private FacilityReservationRepository facilityReservationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<AvailableActivity> mapAvailableActivityDTOsToAvailableActivities(List<AvailableActivityDTO> availableActivityDTOS){
        return availableActivityDTOS
                .stream()
                .map(this::mapAvailableActivityDTOToAvailableActivity)
                .filter(availableActivity -> availableActivity.getActivity() != null && availableActivity.getFacilityReservation() != null)
                .collect(Collectors.toList());
    }

    public AvailableActivity mapAvailableActivityDTOToAvailableActivity(AvailableActivityDTO availableActivityDTO){
        return AvailableActivity.builder()
                .time(availableActivityDTO.getTime())
                .isAvailable(availableActivityDTO.getIsAvailable())
                .activity(activityRepository.findByTitleIgnoreCase(availableActivityDTO.getTitle()).orElse(null))
                .facilityReservation(facilityReservationRepository.findById(availableActivityDTO.getFacilityReservation()).orElse(null))
                .build();
    }
}