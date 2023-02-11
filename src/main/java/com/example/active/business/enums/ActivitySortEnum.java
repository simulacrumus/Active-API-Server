package com.example.active.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivitySortEnum{
    TIME("time"), TITLE("activity.title"), DISTANCE("distance"), ID("id");

    private final String label;

    public static String getSortString(String sort){
        switch (ActivitySortEnum.valueOf(sort.toUpperCase())) {
            case TIME: return TIME.getLabel();
            case TITLE: return TITLE.getLabel();
            case DISTANCE: return DISTANCE.getLabel();
            case ID: return ID.getLabel();
            default: throw new IllegalArgumentException("Invalid sort option");
        }
    }
}
