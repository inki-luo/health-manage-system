package com.springboot.healthmanage.dto;

import lombok.Getter;
import java.time.LocalDate;
import java.sql.Date;

public class DailyCalorie {
    @Getter
    private final LocalDate date;
    @Getter
    private final Long totalIntakeCalories;
//    private final int totalBurnedCalories;

    public DailyCalorie(LocalDate date, Long totalIntakeCalories) {
        this.date = date;
        this.totalIntakeCalories = totalIntakeCalories;
    }
}

