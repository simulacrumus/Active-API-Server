package com.example.active.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "facility")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facility extends BaseEntity {

    @Column(name = "KEYY")
    private String key;

    @Column
    private String title;

    @Column
    private String url;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column(name = "LNG")
    private double longitude;

    @Column(name = "LAT")
    private double latitude;

    @OneToMany(mappedBy = "facility", fetch = FetchType.EAGER)
    private List<FacilityReservation> facilityReservations;
}
