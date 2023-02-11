package com.example.active.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO {
    private Long id;
    private String title;
    private String key;
    private String category;
    private Long activityCount;
}
