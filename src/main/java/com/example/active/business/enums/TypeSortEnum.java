package com.example.active.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeSortEnum {
    ID("id"),TITLE("title"), POPULARITY("popularity");

    private final String label;

    public static String getSortString(String sort){
        switch (TypeSortEnum.valueOf(sort.toUpperCase())) {
            case ID: return ID.getLabel();
            case TITLE: return TITLE.getLabel();
            case POPULARITY: return POPULARITY.getLabel();
            default: throw new IllegalArgumentException("Invalid sort option");
        }
    }
}
