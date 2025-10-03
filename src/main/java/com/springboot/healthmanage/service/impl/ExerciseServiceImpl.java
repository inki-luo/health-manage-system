package com.springboot.healthmanage.service.impl;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.mapper.ExerciseRepository;
import com.springboot.healthmanage.mapper.ExerciseTypeRepository;
import com.springboot.healthmanage.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Override
    public List<Exercise> findByFilter(LocalDate date, String exercise_type_name) {
        // フィルタ条件によってDB検索を分岐
        boolean allType = (exercise_type_name == null || exercise_type_name.isBlank() || "All".equalsIgnoreCase(exercise_type_name));

        // 条件なし → 全件（新しい順）
        if (date == null && allType) {
            return exerciseRepository.findAllOrderByDateDesc();
        }

        // 日付のみ
        if (date != null && allType) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();
            return exerciseRepository.findByDateBetween(start, end);
        }

        // 種別のみ
        if (date == null) {
            return exerciseRepository.findByExerciseType_ExerciseTypeName(exercise_type_name);
        }

        // 日付 + 種別
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return exerciseRepository.findExerciseByTypeAndDate(exercise_type_name, start, end);
    }

    @Override
    public List<Exercise> findByFilter(String dateStr, String exercise_type_name) {
        // Controllerから文字列で来た場合、parseを使う
        LocalDate date = null;
        if (dateStr != null && !dateStr.isBlank()) {
            try {
                date = LocalDate.parse(dateStr);    // yyyy-MM-ddに
            } catch (DateTimeParseException e) {}
        }
        return findByFilter(date, exercise_type_name);
    }

    @Override
    public LinkedHashMap<YearMonth, List<Exercise>> groupByMonth(List<Exercise> exercises) {
        // 月単位でグルーピング（Key は YearMonth）。表示は新しい月→古い月の順。
        return exercises.stream()
                .sorted(Comparator.comparing(Exercise::getDate).reversed())
                .collect(Collectors.groupingBy(
                        e -> YearMonth.from(e.getDate()),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public List<String> findAllTypeNames() {
        // DB 登録済みの運動種別名（アルファベット順）
        return exerciseTypeRepository.findAllExerciseTypeNames();
    }

    @Override
    public Integer getCaloriesBurnedYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime start = yesterday.atStartOfDay();
        LocalDateTime end = yesterday.plusDays(1).atStartOfDay();
        return exerciseRepository
                .findByDateBetween(start, end)
                .stream()
                .mapToInt(Exercise::getKilocalories)
                .sum();
    }

    @Override
    @Transactional  //readOnly = falseに
    public void saveExerciseRecord(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    @Override
    @Transactional
    public void deleteExerciseById(Long id) {
        exerciseRepository.deleteById(id);
    }

}
