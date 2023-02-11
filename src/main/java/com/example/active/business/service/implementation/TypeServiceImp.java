package com.example.active.business.service.implementation;

import com.example.active.business.domain.TypeDTO;
import com.example.active.business.enums.TypeSortEnum;
import com.example.active.business.service.TypeService;
import com.example.active.data.entity.Type;
import com.example.active.data.repository.ActivityRepository;
import com.example.active.data.repository.FacilityActivityRepository;
import com.example.active.data.repository.TypeRepository;
import com.example.active.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TypeServiceImp implements TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private FacilityActivityRepository facilityActivityRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<TypeDTO> findAll(String query, String sortOption) {
        Sort sort = Sort.by(TypeSortEnum.getSortString(sortOption));
        if(sortOption.equalsIgnoreCase(TypeSortEnum.POPULARITY.getLabel())){
               return facilityActivityRepository.findAll()
                    .stream()
                    .collect(Collectors.groupingBy(facilityActivity ->
                            activityRepository.findById(facilityActivity.getFacilityActivityId().getActivityId()).get().getType()
                    ))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> typeMapper.typeToTypeDTO(e.getKey()),
                            e -> e.getValue()
                                    .stream()
                                    .map(facilityActivity -> facilityActivity.getFacilityActivityId().getFacilityId())
                                    .distinct()
                                    .count()
                    ))
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(typeDTOLongEntry -> (typeDTOLongEntry.getValue() * typeDTOLongEntry.getKey().getActivityCount()), Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return typeRepository.findByTitleContains(query, sort)
                .stream()
                .map(type -> typeMapper.typeToTypeDTO(type))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Type type) {
        typeRepository.save(type);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
