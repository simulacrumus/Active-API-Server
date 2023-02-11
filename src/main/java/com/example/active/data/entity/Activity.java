package com.example.active.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Table(name = "activity")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
    @JoinColumn(name="TYPE_ID")
    private Type type;

    @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
    private List<AvailableActivity> availableActivities;
}