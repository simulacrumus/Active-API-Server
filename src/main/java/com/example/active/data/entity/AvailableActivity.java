package com.example.active.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "available_activity")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableActivity extends BaseEntity{

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(name = "AVAILABLE", nullable = false)
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
    @JoinColumn(name="ACTIVITY_ID")
    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
    @JoinColumn(name="FACILITY_RESERVATION_ID")
    private FacilityReservation facilityReservation;
}
