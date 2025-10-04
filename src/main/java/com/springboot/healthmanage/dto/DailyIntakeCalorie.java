package com.springboot.healthmanage.dto;

import lombok.Getter;

import java.time.LocalDate;

public class DailyIntakeCalorie {
    @Getter
    private final LocalDate date;
    @Getter
    private final Long totalIntakeCalories;

    public DailyIntakeCalorie(LocalDate date, Long totalIntakeCalories) {
        this.date = date;
        this.totalIntakeCalories = totalIntakeCalories;
    }
}
