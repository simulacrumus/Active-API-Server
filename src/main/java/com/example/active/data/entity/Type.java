package com.example.active.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "type")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type extends BaseEntity{

    @Column(name = "KEYY")
    private String key;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Activity> activities;
}
