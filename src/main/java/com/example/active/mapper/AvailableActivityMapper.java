package com.example.active.mapper;

import com.example.active.business.domain.AvailableActivityDTO;
import com.example.active.data.entity.AvailableActivity;
import com.example.active.data.repository.ActivityRepository;
import com.example.active.data.repository.FacilityReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AvailableActivityMapper {
    @Autowired
    private FacilityReservationRepository facilityReservationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<AvailableActivity> mapAvailableActivityDTOsToAvailableActivities(List<AvailableActivityDTO> availableActivityDTOS){
        return availableActivityDTOS
                .stream()
                .map(this::mapAvailableActivityDTOToAvailableActivity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public AvailableActivity mapAvailableActivityDTOToAvailableActivity(AvailableActivityDTO availableActivityDTO){
        try{
            return AvailableActivity.builder()
                    .time(availableActivityDTO.getTime())
                    .isAvailable(availableActivityDTO.getIsAvailable())
                    .activity(activityRepository.findByTitleIgnoreCase(availableActivityDTO.getTitle()).orElseThrow())
                    .facilityReservation(facilityReservationRepository.findById(availableActivityDTO.getFacilityReservation()).orElseThrow())
                    .build();
        } catch (NoSuchElementException e){
            log.warn(String.format("%s at %s does not exist in the current activities", availableActivityDTO.getTitle(), availableActivityDTO.getFacilityReservation()));
            return null;
        }
    }
}