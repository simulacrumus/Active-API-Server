package com.example.active.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableActivityDTO {
    private Long id;
    private String title;
    private String facilityReservation;
    private Boolean isAvailable;
    private LocalDateTime time;
    private LocalDateTime lastUpdated;
}
