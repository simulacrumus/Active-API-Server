package com.example.active.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "facility_activity")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityActivity implements Serializable {

    @EmbeddedId
    private FacilityActivityId facilityActivityId;

    @JsonIgnore
    @Column(name = "LAST_UPDATED", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated = LocalDateTime.now();

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
//    @MapsId("activityId")
//    @JoinColumn(name="activity_id")
//    private Activity activity2;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
//    @MapsId("facilityId")
//    @JoinColumn(name="facility_id")
//    private Facility facility2;
}
