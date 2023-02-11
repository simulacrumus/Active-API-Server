package com.example.active.util;

public class DistanceFormatter {
    public static String format(Double distance){
        if(distance.intValue() < 1000){
            return String.format("%dm", distance.intValue());
        } else {
            return String.format("%.1fkm", distance / 1000);
        }
    }
}
