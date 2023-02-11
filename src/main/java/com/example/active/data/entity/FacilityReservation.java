package com.example.active.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "facility_reservation")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityReservation {

    @Id
    private String id;

    @Column(name = "LAST_UPDATED", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
    @JoinColumn(name="FACILITY_ID")
    private Facility facility;

    @OneToMany(mappedBy = "facilityReservation", fetch = FetchType.LAZY)
    private List<AvailableActivity> availableActivities;
}
