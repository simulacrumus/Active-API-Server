package com.example.active.mapper;

import com.example.active.business.domain.FacilityDTO;
import com.example.active.data.entity.Facility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FacilityMapper {

    @Value( "${reservation-url.prefix}" )
    private String reservationURLPrefix;

    public FacilityDTO facilityToFacilityDTO(Facility facility){
        return FacilityDTO.builder()
                .id(facility.getId())
                .key(facility.getKey())
                .title(facility.getTitle())
                .address(facility.getAddress())
                .email(facility.getEmail())
                .phone(facility.getPhone())
                .lat(facility.getLatitude())
                .lng(facility.getLongitude())
                .url(facility.getUrl())
                .distance(0.0)
                .build();
    }
}