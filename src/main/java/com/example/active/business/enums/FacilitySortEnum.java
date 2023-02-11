package com.example.active.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FacilitySortEnum {
    ID("id"),TITLE("title"), DISTANCE("distance");

    private final String label;

    public static String getSortString(String sort){
        switch (ActivitySortEnum.valueOf(sort.toUpperCase())) {
            case ID: return ID.getLabel();
            case TITLE: return TITLE.getLabel();
            case DISTANCE: return DISTANCE.getLabel();
            default: throw new IllegalArgumentException("Invalid sort option");
        }
    }
}
