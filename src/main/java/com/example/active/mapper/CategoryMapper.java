package com.example.active.mapper;

import com.example.active.business.domain.CategoryDTO;
import com.example.active.business.domain.TypeDTO;
import com.example.active.data.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    @Autowired
    private TypeMapper typeMapper;
    public CategoryDTO categoryToCategoryDTO(Category category){
        List<TypeDTO> typeDTOS = category.getTypes()
                .stream()
                .map(type -> typeMapper.typeToTypeDTO(type))
                .sorted(Comparator.comparing(TypeDTO::getTitle))
                .collect(Collectors.toList());
        return CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .key(category.getKey())
                .activityTypes(typeDTOS)
                .build();
    }
}
