package com.example.active.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "category")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{

    @Column(name = "KEYY")
    private String key;

    @Column
    private String title;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Type> types;
}
