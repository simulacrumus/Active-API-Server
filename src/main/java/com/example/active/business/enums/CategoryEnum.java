package com.example.active.business.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CategoryEnum {
    SWIMMING("swimming"),
    SPORTS("sports"),
    SKATING("skating"),
    FITNESS("fitness"),
    OTHER("other");
    private final String label;
}
