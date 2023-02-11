package com.example.active.business.service.implementation;

import com.example.active.business.domain.FacilityDTO;
import com.example.active.business.enums.FacilitySortEnum;
import com.example.active.business.service.FacilityService;
import com.example.active.data.entity.*;
import com.example.active.data.repository.*;
import com.example.active.mapper.FacilityMapper;
import org.apache.lucene.util.SloppyMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityServiceImp implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private AvailableActivityRepository availableActivityRepository;
    @Autowired
    private FacilityMapper facilityMapper;
    @Autowired
    private FacilityActivityRepository facilityActivityRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<FacilityDTO> findAll(String query, String sortOption, Double lat, Double lng) {
        Sort sort = Sort.by(FacilitySortEnum.getSortString(sortOption));
        List<Facility> facilities = sortOption.equalsIgnoreCase(FacilitySortEnum.DISTANCE.getLabel()) ?
                facilityRepository.findByTitleContains(query) :
                facilityRepository.findByTitleContains(query, sort);
        List<FacilityDTO> facilityDTOS = facilities.stream()
                .map(facility -> {
                    FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);
                    facilityDTO.setDistance(
                            SloppyMath.haversinMeters(lat, lng, facility.getLatitude(), facility.getLongitude()));
                    return facilityDTO;
                })
                .collect(Collectors.toList());
        if(sortOption.equalsIgnoreCase(FacilitySortEnum.DISTANCE.getLabel()))
            facilityDTOS.sort(Comparator.comparing(FacilityDTO::getDistance));
        return facilityDTOS;
    }


    @Override
    public List<FacilityDTO> findByType(String query, String type, String sort, Double lat, Double lng) {
        List<Long> activityIdsForType = activityRepository.findByTypeKey(type)
                .stream()
                .map(BaseEntity::getId)
                .distinct()
                .collect(Collectors.toList());

        return  facilityActivityRepository.findAllByFacilityActivityIdActivityIdIn(activityIdsForType)
                .stream()
                .map(facilityActivity -> facilityActivity.getFacilityActivityId().getFacilityId())
                .map(facilityId -> facilityRepository.findById(facilityId))
                .map(facility -> facilityMapper.facilityToFacilityDTO(facility.get()))
                .distinct()
                .sorted(Comparator.comparing(FacilityDTO::getTitle))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Facility facility) {
        facilityRepository.save(facility);
    }

    @Override
    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }

    @Override
    public Optional<FacilityDTO> findById(Long id, Double lat, Double lng) {
        Optional<Facility> facility = facilityRepository.findById(id);
        if(facility.isPresent()){
            double facilityLat = facility.get().getLatitude();
            double facilityLng = facility.get().getLongitude();
            Optional<FacilityDTO> facilityDTO =
                    Optional.ofNullable(facilityMapper.facilityToFacilityDTO(facility.get()));
            facilityDTO.get().setDistance(SloppyMath.haversinMeters(lat, lng, facilityLat, facilityLng));
            return facilityDTO;
        }
        return Optional.empty();
    }

    @Override
    public Optional<FacilityDTO> findByKey(String key, Double lat, Double lng) {
        Optional<Facility> facility = facilityRepository.findByKey(key);
        if(facility.isPresent()){
            double facilityLat = facility.get().getLatitude();
            double facilityLng = facility.get().getLongitude();
            Optional<FacilityDTO> facilityDTO =
                    Optional.ofNullable(facilityMapper.facilityToFacilityDTO(facility.get()));
            facilityDTO.get().setDistance(SloppyMath.haversinMeters(lat, lng, facilityLat, facilityLng));
            return facilityDTO;
        }
        return Optional.empty();
    }

    @Override
    public Map<Long, FacilityDTO> findAllWithDistance(Sort sort, Double lat, Double lng){
        return facilityRepository.findAll(sort)
                .stream()
                .collect(Collectors.toMap(
                        Facility::getId,
                        facility -> {
                            FacilityDTO facilityDTO = facilityMapper.facilityToFacilityDTO(facility);
                            facilityDTO.setDistance(
                                    SloppyMath.haversinMeters(lat, lng, facility.getLatitude(), facility.getLongitude()));
                            return facilityDTO;
                        }));
    }

//    @Override
//    public Map<String, ActivityFacilityDTO> getActivityFacilityDTOsWithDistance(Sort sort, Double lat, Double lng){
//         return getFacilityDTOsWithDistance(sort, lat, lng)
//                 .entrySet()
//                 .stream()
//                 .collect(Collectors.toMap(
//                         Map.Entry::getKey,
//                         (entry) -> facilityMapper.facilityDTOToActivityFacilityDTO(entry.getValue()))
//                 );
//    }
}
