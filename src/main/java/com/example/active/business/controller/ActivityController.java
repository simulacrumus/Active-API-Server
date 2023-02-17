package com.example.active.business.controller;

import com.example.active.business.domain.ActivityDTO;
import com.example.active.business.domain.AvailableActivityDTO;
import com.example.active.business.service.ActivityService;
import com.example.active.business.service.FacilityService;
import com.example.active.data.entity.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class ActivityController {
    private final String DEFAULT_NUM_OF_RESULTS_PER_PAGE = "20";
    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_ACTIVITY_SORT_OPTION = "time";
    private final String DEFAULT_LONGITUDE = "-75.68954";
    private final String DEFAULT_LATITUDE = "45.42001";

    @Autowired
    private ApiKeyAuthenticator apiKeyAuthenticator;
    @Autowired
    private FacilityService facilityService;
    @Autowired
    @Qualifier("ActivityServiceImp")
    private ActivityService activityService;

    @RequestMapping(value = "/activities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivities(
        @RequestParam(name = "key", required = false) String apiKey,
        @RequestParam(name = "q", defaultValue = "") String query,
        @RequestParam(name = "available") Optional<Boolean> isAvailable,
        @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
        @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
        @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
        @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
        @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
        HttpServletRequest request,
        HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findAll(query, isAvailable, page, size, sort, lat, lng);
        return activities;
    }

    @RequestMapping(
            value = "/activities/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public Optional<ActivityDTO> getActivityById(
            @PathVariable("id") Long id,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return activityService.findById(id, lat, lng);
    }

    @RequestMapping(
            value = "/categories/{category}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByCategory(
            @PathVariable("category") String category,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findByCategory(query, category, isAvailable, page, size, sort, lat, lng);
        return activities;
    }

    @RequestMapping(
            value = "/categories/{category}/types/{type}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByCategoryAndType(
            @PathVariable("category") String category,
            @PathVariable("type") String type,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        Page<ActivityDTO> activities = activityService.findByCategoryAndType(
                query, isAvailable, category, type, page, size, sort, lat, lng);
        return activities;
    }

    @RequestMapping(
            value = "activities/types/{type}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByType(
            @PathVariable("type") String type,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findByType(query, isAvailable, type, page, size, sort, lat, lng);
        return activities;
    }

    @RequestMapping(value = "/facilities/{facility}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByFacility(
            @PathVariable("facility") String facility,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findByFacility(query, isAvailable,facility,page,size,sort,lat,lng);
        return activities;
    }

    @RequestMapping(value = "/facilities/{facility}/categories/{category}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByFacilityAndCategory(
            @PathVariable("facility") String facility,
            @PathVariable("category") String category,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findByFacilityAndCategory(query, isAvailable, facility,category,page,size,sort,lat,lng);
        return activities;
    }

    @RequestMapping(value = "/facilities/{facility}/categories/{category}/types/{type}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByFacilityAndCategoryAndType(
            @PathVariable("facility") String facility,
            @PathVariable("category") String category,
            @PathVariable("type") String type,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findByFacilityAndCategoryAndType(query, isAvailable, facility,category, type, page,size,sort,lat,lng);
        return activities;
    }

    @RequestMapping(value = "/facilities/{facility}/types/{type}/activities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDTO> getActivitiesByFacilityAndType(
            @PathVariable("facility") String facility,
            @PathVariable("type") String type,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "available") Optional<Boolean> isAvailable,
            @RequestParam(name = "lng", defaultValue = DEFAULT_LONGITUDE) Double lng,
            @RequestParam(name = "lat", defaultValue = DEFAULT_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_ACTIVITY_SORT_OPTION) String sort,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_NUM_OF_RESULTS_PER_PAGE) Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Page<ActivityDTO> activities = activityService.findByFacilityAndType(query, isAvailable, facility, type, page,size,sort,lat,lng);
        return activities;
    }

    @RequestMapping(
            value = "/activities/all",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAvailableActivities(
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody List<AvailableActivityDTO> availableActivityDTOS
    ){
        activityService.saveAvailableActivities(availableActivityDTOS);
    }

    @RequestMapping(
            value = "/activities",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveActivity(
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Activity activity
    ){
        if(!apiKeyAuthenticator.isAuthenticated(apiKey)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key");
        }
        activityService.saveActivity(activity);
    }

    @RequestMapping(
            value = "/activities/{id}",
            method = RequestMethod.DELETE,
            consumes = {"*/*"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivity(
            @RequestParam(name = "key") String apiKey,
            @PathVariable("id") Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(!apiKeyAuthenticator.isAuthenticated(apiKey)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key");
        }
        try{
            activityService.deleteActivity(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No activity found with id %d", id));
        }
    }
}
