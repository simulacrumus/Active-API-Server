package com.example.active.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeEnum {
    FIFTY_PLUS_SKATING("fiftyplusskating"),
    FIFTY_PLUS_SWIMMING("fiftyplusswimming"),
    ADULT_SKATING("adultskating"),
    ALTERNATE_SWIM("alternateswim"),
    AQUA("aqua"),
    BADMINTON("badminton"),
    BASKETBALL("basketball"),
    CARDIO("cardio"),
    CONDITIONING("conditioning"),
    DODGEBALL("dodgeball"),
    CURLING("curling"),
    FAMILY_SKATING("familyskating"),
    FIGURE_SKATING("figureskating"),
    GYM("gym"),
    HOCKEY("hockey"),
    LANE_SWIM("laneswim"),
    OTHER("other"),
    PICKLEBALL("pickleball"),
    PILATES("pilates"),
    PRESCHOOL_SWIM("preschoolswim"),
    PUBLIC_SKATING("publicskating"),
    PUBLIC_SWIM("publicswim"),
    RACQUETBALL("racquetball"),
    SHINNY("shinny"),
    SOCCER("soccer"),
    SQUASH("squash"),
    STRENGTH("strength"),
    TABLE_TENNNIS("tabletennis"),
    VOLLEYBALL("volleyball"),
    WAVE_SWIM("waveswim"),
    WEIGHT_AND_CARDIO_ROOM("weightandcardioroom"),
    WOMENS_ONLY_SWIM("womensonlyswim"),
    YOGA("yoga"),
    ZUMBA("zumba");

    private final String label;
}
