package com.example.active.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {
    private Long id;
    private String key;
    private String title;
    private String address;
    private String phone;
    private String email;
    private String url;
    private Double lat;
    private Double lng;
    private Double distance;
}
