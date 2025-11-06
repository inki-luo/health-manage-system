package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.dto.WorkoutData;
import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.service.ExerciseService;
import com.springboot.healthmanage.service.ExerciseTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")   //APIのベースURL
public class WorkoutController {

    private final ExerciseTypeService exerciseTypeService;
    private final ExerciseService exerciseService;

    public WorkoutController(ExerciseTypeService exerciseTypeService, ExerciseService exerciseService) {
        this.exerciseTypeService = exerciseTypeService;
        this.exerciseService = exerciseService;
    }

    // Formatter for HealthKit Date data
    private static final DateTimeFormatter HEALTHKIT_FORMATTER = new DateTimeFormatterBuilder()
            // 1. "2025-10-24T12:43:20.996" の部分を解析
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            // 2. "+0900" (コロン無しオフセット) の部分を解析
            .appendOffset("+HHMM", "Z")
            .toFormatter();

    @PostMapping("/workouts")
    public Map<String, String> receiveWorkoutData(@RequestBody List<WorkoutData> workoutDataList) {
        System.out.println("receive workout data: " + workoutDataList.size() + "items");

        // DTO (WorkoutData) から Entity (Exercise) に変換
        try {
            for(WorkoutData workoutData : workoutDataList) {
                Exercise exercise = new Exercise();

                // startDate(String) --> startDate(LocalDateTime)
                // +0900形式を解析
                OffsetDateTime odt = OffsetDateTime.parse(workoutData.getStartDate(), HEALTHKIT_FORMATTER);
                // サーバーのタイムゾーン（例: JST）のLocalDateTimeに変換
                LocalDateTime dateTime = odt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                // ミリ秒を切り捨てる
//                LocalDateTime truncatedDateTime = dateTime.truncatedTo(ChronoUnit.SECONDS);
                exercise.setDate(dateTime);

                // calories(Double) --> kilocalories(Integer)
                exercise.setKilocalories(workoutData.getCalories().intValue());

                // activityName(String) --> exerciseType(ExerciseType)
                ExerciseType exerciseType = exerciseTypeService.findOrCreateExerciseTypeByName(workoutData.getActivityName());
                exercise.setExerciseType(exerciseType);

                exerciseService.saveExerciseRecord(exercise);
            }
            // iOSアプリに「成功」のJSONを返す
            return Map.of("status", "success", "received_records", String.valueOf(workoutDataList.size()));
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            // iOSアプリに「失敗」のJSONを返す
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}
