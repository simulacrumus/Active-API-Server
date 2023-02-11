package com.example.active.business.domain;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO{
    private Long id;
    private String title;
    private String type;
    private String category;
    private String reservationURL;
    private Boolean isAvailable;
    private FacilityDTO facility;
    private LocalDateTime time;
    private LocalDateTime lastUpdated;
}
