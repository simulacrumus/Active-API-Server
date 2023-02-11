package com.example.active.business.service;

import com.example.active.business.domain.CategoryDTO;
import com.example.active.data.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> findAll();
    Optional<CategoryDTO> findById(Long id);
    List<CategoryDTO> findByFacility(String facility);

    void save(Category category);

    void deleteCategory(Long id);
}
