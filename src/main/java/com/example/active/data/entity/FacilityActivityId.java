package com.example.active.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityActivityId implements Serializable {
    @Column(name = "FACILITY_ID")
    private Long facilityId;

    @Column(name = "ACTIVITY_ID")
    private Long activityId;
}
