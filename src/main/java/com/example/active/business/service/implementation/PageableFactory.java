package com.example.active.business.service.implementation;

import com.example.active.business.enums.ActivitySortEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageableFactory {
    public static Pageable getPageable(Integer page, Integer size, String sortOption){
        String sortString = ActivitySortEnum.getSortString(sortOption);
        Sort sort = Sort.by(Sort.DEFAULT_DIRECTION, sortString)
                .and(Sort.by(ActivitySortEnum.TIME.getLabel()))
                .and(Sort.by(ActivitySortEnum.TITLE.getLabel()));
        return PageRequest.of(page, size, sort);
    }
}
