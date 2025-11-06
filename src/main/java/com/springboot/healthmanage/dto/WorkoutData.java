package com.springboot.healthmanage.dto;

import lombok.Getter;

public class WorkoutData {

    @Getter
    private String activityName;
    @Getter
    private Double calories;
    @Getter
    private String startDate;
//    @Getter
//    private String endDate;
}
