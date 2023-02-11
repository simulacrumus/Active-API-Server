package com.example.active.mapper;

import com.example.active.business.domain.ActivityDTO;
import com.example.active.data.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityMapper {

    @Autowired
    private FacilityMapper facilityMapper;
    @Value( "${reservation-url.prefix}" )
    private String reservationURLPrefix;
    public ActivityDTO mapAvailableActivityToActivityDTO(AvailableActivity availableActivity){
        return ActivityDTO.builder()
                .id(availableActivity.getId())
                .title(availableActivity.getActivity().getTitle())
                .type(availableActivity.getActivity().getType().getTitle())
                .category(availableActivity.getActivity().getType().getCategory().getTitle())
                .reservationURL(reservationURLPrefix.concat(availableActivity.getFacilityReservation().getId()))
                .time(availableActivity.getTime())
                .isAvailable(availableActivity.getIsAvailable())
                .lastUpdated(availableActivity.getLastUpdated())
                .facility(facilityMapper.facilityToFacilityDTO(availableActivity.getFacilityReservation().getFacility()))
                .build();
    }

    public List<ActivityDTO> mapAvailableActivitiesToDTO(List<AvailableActivity> activities){
        return activities
                .stream()
                .map(this::mapAvailableActivityToActivityDTO)
                .collect(Collectors.toList());
    }

    public Page<ActivityDTO> mapAvailableActivityPageToActivityDTOPage(Page<AvailableActivity> page){
        return new PageImpl<>(
                mapAvailableActivitiesToDTO(page.getContent()),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    public Page<ActivityDTO> mapActivityDTOSToActivityDTOPage(List<ActivityDTO> activityDTOS, Pageable pageable){
        int start = (int) pageable.getOffset();
        int end = (Math.min((start + pageable.getPageSize()), activityDTOS.size()));
        return new PageImpl<>(activityDTOS.subList(start, end), pageable, activityDTOS.size());
    }
}