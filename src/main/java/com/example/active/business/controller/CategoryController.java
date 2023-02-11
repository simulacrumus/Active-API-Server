package com.example.active.business.controller;

import com.example.active.business.domain.CategoryDTO;
import com.example.active.business.service.CategoryService;
import com.example.active.data.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(
            value = "/categories",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getCategories(
            @RequestParam(name = "key") String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return categoryService.findAll();
    }

    @RequestMapping(
            value = "/categories/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public Optional<CategoryDTO> getCategoryById(
            @PathVariable("id") Long id,
            @RequestParam(name = "key") String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        return categoryService.findById(id);
    }

    @RequestMapping(value = "/facilities/{facility}/categories",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getCategoriesByFacility(
            @PathVariable("facility") String facility,
            @RequestParam(name = "key") String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return categoryService.findByFacility(facility);
    }

    @RequestMapping(
            value = "/categories",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCategory(
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Category category
    ){
        categoryService.save(category);
    }

    @RequestMapping(
            value = "/categories/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveAvailableActivities(
            @RequestParam(name = "key") String apiKey,
            @PathVariable("id") Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        categoryService.deleteCategory(id);
    }
}
