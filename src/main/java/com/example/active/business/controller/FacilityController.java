package com.example.active.business.controller;

import com.example.active.business.domain.FacilityDTO;
import com.example.active.business.service.FacilityService;
import com.example.active.data.entity.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class FacilityController {

    private final String DEFAULT_FACILITY_SORT_OPTION = "title";
    private final String DEFAULT_OTTAWA_LONGITUDE = "-75.68954";
    private final String DEFAULT_OTTAWA_LATITUDE = "45.42001";
    @Autowired
    private FacilityService facilityService;
    @Autowired
    private ApiKeyAuthenticator apiKeyAuthenticator;
    @RequestMapping(value = "/facilities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityDTO> getFacilities(
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "lng", required = false, defaultValue = DEFAULT_OTTAWA_LONGITUDE) Double lng,
            @RequestParam(name = "lat", required = false, defaultValue = DEFAULT_OTTAWA_LATITUDE) Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_FACILITY_SORT_OPTION) String sort,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return facilityService.findAll(query, sort, lat, lng);
    }

    @RequestMapping(value = "/facilities/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<FacilityDTO> getFacilityByKey(
            @PathVariable("id") String key,
            @RequestParam(name = "lng", required = false, defaultValue = DEFAULT_OTTAWA_LONGITUDE) Double lng,
            @RequestParam(name = "lat", required = false, defaultValue = DEFAULT_OTTAWA_LATITUDE) Double lat,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return facilityService.findByKey(key, lat, lng);
    }

    @RequestMapping(value = "/types/{type}/facilities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityDTO> getFacilitiesByType(
            @PathVariable("type") String type,
            @RequestParam(name = "sort", required = false, defaultValue = DEFAULT_FACILITY_SORT_OPTION) String sort,
            @RequestParam(name = "q", required = false, defaultValue = "") String query,
            @RequestParam(name = "lng", required = false, defaultValue = DEFAULT_OTTAWA_LONGITUDE) Double lng,
            @RequestParam(name = "lat", required = false, defaultValue = DEFAULT_OTTAWA_LATITUDE) Double lat,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        try{
            return facilityService.findByType(query, type, sort, lat, lng);
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort option");
        }
    }

    @RequestMapping(
            value = "/facilities",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFacility(
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Facility facility
    ){
        if(!apiKeyAuthenticator.isAuthenticated(apiKey)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key");
        }
        facilityService.save(facility);
    }

    @RequestMapping(value = "/facilities/{id}",
            method = RequestMethod.DELETE,
            consumes = {"*/*"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacility(
            @PathVariable("id") Long id,
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(!apiKeyAuthenticator.isAuthenticated(apiKey)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key");
        }
        try{
            facilityService.deleteFacility(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No facility found with id %d", id));
        }
    }
}