package com.example.active.business.controller;

import com.example.active.business.domain.FacilityDTO;
import com.example.active.business.domain.TypeDTO;
import com.example.active.business.service.FacilityService;
import com.example.active.business.service.TypeService;
import com.example.active.data.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/types")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private FacilityService facilityService;
    @Autowired
    private ApiKeyAuthenticator apiKeyAuthenticator;
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<TypeDTO> getTypes(
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "sort") String sort,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return typeService.findAll(query, sort);
    }

    @RequestMapping(value = "/{type}/facilities",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityDTO> getFacilitiesByType(
            @PathVariable("type") String type,
            @RequestParam(name = "key", required = false) String apiKey,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "lng") Double lng,
            @RequestParam(name = "lat") Double lat,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return facilityService.findByType(query, type, sort, lat, lng);
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAvailableActivities(
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Type type
    ){
        if(!apiKeyAuthenticator.isAuthenticated(apiKey)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key");
        }
        typeService.save(type);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            consumes = {"*/*"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteType(
            @PathVariable("id") Long id,
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(!apiKeyAuthenticator.isAuthenticated(apiKey)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key");
        }
        try{
            typeService.deleteType(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No type found with id %d", id));
        }
    }
}
