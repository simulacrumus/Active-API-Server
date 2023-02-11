package com.example.active.mapper;

import com.example.active.business.domain.TypeDTO;
import com.example.active.data.entity.Type;
import org.springframework.stereotype.Component;

@Component
public class TypeMapper {
    public TypeDTO typeToTypeDTO(Type type){
        return TypeDTO.builder()
                .id(type.getId())
                .title(type.getTitle())
                .category(type.getCategory().getTitle())
                .key(type.getKey())
                .activityCount(type.getActivities().stream()
                        .flatMap(activity -> activity.getAvailableActivities().stream())
                        .count()
                )
                .build();
    }
}
