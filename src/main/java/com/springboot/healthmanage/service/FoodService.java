package com.springboot.healthmanage.service;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.Food;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;

public interface FoodService {
    List<Food> findByFilter(LocalDate date, String food_name);

    List<Food> findByFilter(String dateStr, String food_name);

    LinkedHashMap<YearMonth, List<Food>> groupByMonth(List<Food> food);

    List<String> findAllTypeNames();

    Integer getCaloriesIntakeYesterday();

    void saveFoodRecord(Food food);

    void deleteFoodById(Long id);

    LinkedHashMap<LocalDate, Integer> getDailyIntakeForLast7Days();

    List<Food> getRecentFoodRecords();
}