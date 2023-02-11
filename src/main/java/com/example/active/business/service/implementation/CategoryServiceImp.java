package com.example.active.business.service.implementation;

import com.example.active.business.domain.CategoryDTO;
import com.example.active.business.service.CategoryService;
import com.example.active.data.entity.*;
import com.example.active.data.repository.ActivityRepository;
import com.example.active.data.repository.CategoryRepository;
import com.example.active.data.repository.FacilityActivityRepository;
import com.example.active.data.repository.FacilityRepository;
import com.example.active.mapper.CategoryMapper;
import com.example.active.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FacilityActivityRepository facilityActivityRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories
                .stream()
                .map(category -> categoryMapper.categoryToCategoryDTO(category))
                .sorted(Comparator.comparing(CategoryDTO::getTitle))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(value -> categoryMapper.categoryToCategoryDTO(value));
    }

    @Override
    public List<CategoryDTO> findByFacility(String facilityKey) {
        Optional<Facility> facility = facilityRepository.findByKey(facilityKey);
        return facilityActivityRepository.findByFacilityActivityIdFacilityId(facility.get().getId())
                .stream()
                .map(facilityActivity -> activityRepository.findById(facilityActivity.getFacilityActivityId().getActivityId()).get().getType())
                .collect(Collectors.groupingBy(
                        (entry) -> categoryMapper.categoryToCategoryDTO(entry.getCategory())
                ))
                .entrySet()
                .stream()
                .map(categoryDTOListEntry -> {
                    CategoryDTO categoryDTO = categoryDTOListEntry.getKey();
                    categoryDTO.setActivityTypes(categoryDTOListEntry.getValue().stream().map(type -> typeMapper.typeToTypeDTO(type)).distinct().collect(Collectors.toList()));
                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
