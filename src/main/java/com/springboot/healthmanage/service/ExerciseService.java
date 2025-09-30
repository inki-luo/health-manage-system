package com.springboot.healthmanage.service;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.mapper.ExerciseRepository;
import com.springboot.healthmanage.mapper.ExerciseTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;

public interface ExerciseService {
    List<Exercise> findByFilter(LocalDate date, String exercise_type_name);

    List<Exercise> findByFilter(String dateStr, String exercise_type_name);

    LinkedHashMap<YearMonth, List<Exercise>> groupByMonth(List<Exercise> exercises);

    List<String> findAllTypeNames();

    Integer getCaloriesBurnedYesterday();

    void saveExerciseRecord(Exercise exercise);
}