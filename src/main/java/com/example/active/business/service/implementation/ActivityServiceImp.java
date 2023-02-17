package com.example.active.business.service.implementation;

import com.example.active.business.domain.ActivityDTO;
import com.example.active.business.domain.AvailableActivityDTO;
import com.example.active.business.domain.FacilityDTO;
import com.example.active.business.enums.ActivitySortEnum;
import com.example.active.business.enums.FacilitySortEnum;
import com.example.active.business.service.ActivityService;
import com.example.active.business.service.FacilityService;
import com.example.active.data.entity.Activity;
import com.example.active.data.entity.AvailableActivity;
import com.example.active.data.repository.ActivityRepository;
import com.example.active.data.repository.AvailableActivityRepository;
import com.example.active.data.repository.FacilityReservationRepository;
import com.example.active.mapper.ActivityMapper;
import com.example.active.mapper.AvailableActivityMapper;
import org.apache.lucene.util.SloppyMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Qualifier("ActivityServiceImp")
public class ActivityServiceImp implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private AvailableActivityMapper availableActivityMapper;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private AvailableActivityRepository availableActivityRepository;
    @Autowired
    private FacilityService facilityService;
    @Autowired
    private FacilityReservationRepository facilityReservationRepository;


    @Override
    public Optional<ActivityDTO> findById(Long id, Double lat, Double lng) {
        Optional<AvailableActivity> availableActivity = availableActivityRepository.findById(id);
        if(availableActivity.isPresent()){
            double facilityLat = availableActivity.get().getFacilityReservation().getFacility().getLatitude();
            double facilityLng = availableActivity.get().getFacilityReservation().getFacility().getLongitude();
            Optional<ActivityDTO> activityDTO =
                    Optional.ofNullable(activityMapper.mapAvailableActivityToActivityDTO(availableActivity.get()));
            activityDTO.get().getFacility().setDistance(SloppyMath.haversinMeters(lat, lng, facilityLat, facilityLng));
            return activityDTO;
        }
        return Optional.empty();
    }

    @Override
    public Page<ActivityDTO> findAll(
            String query,  Optional<Boolean> isAvailable, Integer page, Integer size, String sortOption, Double lat, Double lng) {
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            availableActivityPage = availableActivityRepository
                    .findAll(pageable, query, isAvailable.orElse(null), lat, lng);
        } else {
            if(isAvailable.isEmpty()){
                availableActivityPage = availableActivityRepository
                        .findByActivityTitleContainsAndTimeAfter(pageable, query, LocalDateTime.now());
            } else {
                availableActivityPage = availableActivityRepository
                        .findByActivityTitleContainsAndIsAvailableAndTimeAfter(pageable, query, isAvailable.get(), LocalDateTime.now());
            }
        }
        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByCategory(
            String query, String category, Optional<Boolean> isAvailable, Integer page, Integer size, String sortOption, Double lat, Double lng) {
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            availableActivityPage = availableActivityRepository
                    .findByCategory(pageable, query, category, isAvailable.orElse(null), lat, lng);
        } else {
            if(isAvailable.isEmpty()){
                availableActivityPage = availableActivityRepository
                        .findByActivityTypeCategoryKeyAndActivityTitleContainsAndTimeAfter(pageable, query, category, LocalDateTime.now());
            } else {
                availableActivityPage = availableActivityRepository
                        .findByActivityTypeCategoryKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(pageable, query, category, isAvailable.get(), LocalDateTime.now());
            }
        }
        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByCategoryAndType(
            String query, Optional<Boolean> isAvailable, String category, String type, Integer page, Integer size, String sortOption, Double lat, Double lng) {
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            availableActivityPage = availableActivityRepository
                    .findByCategoryAndType(pageable, query, category, isAvailable.orElse(null), type, lat, lng);
        } else {
            if(isAvailable.isEmpty()){
                availableActivityPage = availableActivityRepository
                        .findAvailableActivitiesByActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndTimeAfter(pageable, category, type, query, LocalDateTime.now());
            } else {
                availableActivityPage = availableActivityRepository
                        .findAvailableActivitiesByActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(pageable, category, type, query, isAvailable.get(), LocalDateTime.now());
            }

        }
        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByType(String query, Optional<Boolean> isAvailable, String type, Integer page, Integer size, String sortOption, Double lat, Double lng) {

        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            availableActivityPage = availableActivityRepository
                    .findByType(pageable, query, isAvailable.orElse(null), type, lat, lng);
        } else {
            if(isAvailable.isEmpty()){
                availableActivityPage = availableActivityRepository
                        .findAvailableActivitiesByActivityTypeKeyAndActivityTitleContainsAndTimeAfter(pageable, type, query, LocalDateTime.now());
            } else {
                availableActivityPage = availableActivityRepository
                        .findAvailableActivitiesByActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(pageable, type, query, isAvailable.get(), LocalDateTime.now());
            }
        }
        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByFacility(
            String query, Optional<Boolean> isAvailable, String facility, Integer page, Integer size, String sortOption, Double lat, Double lng) {
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            sortOption = ActivitySortEnum.TIME.getLabel();
        }
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if (isAvailable.isEmpty()) {
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTitleContainsAndTimeAfter(
                            pageable,facility, query, LocalDateTime.now());
        }else{
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
                            pageable,facility, query, isAvailable.get(), LocalDateTime.now());
        }

        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByFacilityAndCategory(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            String category,
            Integer page,
            Integer size,
            String sortOption,
            Double lat,
            Double lng) {
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            sortOption = ActivitySortEnum.TIME.getLabel();
        }
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(isAvailable.isEmpty()){
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
                            pageable, facility, category, query, isAvailable.get(), LocalDateTime.now());
        } else {
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTitleContainsAndTimeAfter(
                            pageable, facility, category, query, LocalDateTime.now());
        }
        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByFacilityAndCategoryAndType(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            String category,
            String type,
            Integer page,
            Integer size,
            String sortOption,
            Double lat,
            Double lng) {

        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            sortOption = ActivitySortEnum.TIME.getLabel(); // If facility is specified, no need to sort by distance, instead sort by time
        }
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(isAvailable.isEmpty()){
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndTimeAfter(
                            pageable,facility, category, type, query, LocalDateTime.now());
        } else {
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeCategoryKeyAndActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
                            pageable,facility, category, type, query, isAvailable.get(), LocalDateTime.now());
        }
        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public Page<ActivityDTO> findByFacilityAndType(
            String query,
            Optional<Boolean> isAvailable,
            String facility,
            String type,
            Integer page,
            Integer size,
            String sortOption,
            Double lat,
            Double lng) {
        if(sortOption.equalsIgnoreCase(ActivitySortEnum.DISTANCE.getLabel())){
            sortOption = ActivitySortEnum.TIME.getLabel();
        }
        Pageable pageable = PageableFactory.getPageable(page, size, sortOption);
        Page<AvailableActivity> availableActivityPage;
        if(isAvailable.isEmpty()){
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeKeyAndActivityTitleContainsAndTimeAfter(
                            pageable, facility, type, query, LocalDateTime.now());
        } else {
            availableActivityPage = availableActivityRepository
                    .findAvailableActivitiesByFacilityReservationFacilityKeyAndActivityTypeKeyAndActivityTitleContainsAndIsAvailableAndTimeAfter(
                            pageable, facility, type, query, isAvailable.get(), LocalDateTime.now());
        }

        return getActivityDTOPageWithFacilityDistance(availableActivityPage, lat, lng);
    }

    @Override
    public void saveAvailableActivities(List<AvailableActivityDTO> availableActivityDTOS) {
        if(!availableActivityDTOS.isEmpty()){
            List<AvailableActivity> availableActivities =
                    availableActivityMapper.mapAvailableActivityDTOsToAvailableActivities(availableActivityDTOS);
            availableActivityRepository.deleteAll(); // Truncate table first
            availableActivityRepository.saveAll(availableActivities); // Reload table
        }
    }

    @Override
    public void saveAvailableActivity(AvailableActivityDTO availableActivityDTO) {
        availableActivityRepository.save(availableActivityMapper.mapAvailableActivityDTOToAvailableActivity(availableActivityDTO));
    }

    @Override
    public void saveActivity(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    private Page<ActivityDTO> getActivityDTOPageWithFacilityDistance(Page<AvailableActivity> availableActivityPage, Double lat, Double lng){
        Map<Long, FacilityDTO> facilityDTOMap =
                facilityService.findAllWithDistance(
                        Sort.by(FacilitySortEnum.TITLE.getLabel()), lat, lng);
        Page<ActivityDTO> activityDTOPage =
                activityMapper.mapAvailableActivityPageToActivityDTOPage(availableActivityPage);
        activityDTOPage.getContent().forEach(activityDTO ->
                activityDTO.setFacility(facilityDTOMap.get(activityDTO.getFacility().getId())));
        return activityDTOPage;
    }
}