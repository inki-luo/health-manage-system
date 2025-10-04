package com.springboot.healthmanage.service.impl;

import com.springboot.healthmanage.dto.DailyIntakeCalorie;
import com.springboot.healthmanage.dto.DailyIntakeCalorie;
import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.Food;
import com.springboot.healthmanage.mapper.ExerciseTypeRepository;
import com.springboot.healthmanage.mapper.FoodRepository;
import com.springboot.healthmanage.mapper.FoodTypeRepository;
import com.springboot.healthmanage.service.FoodService;
import lombok.RequiredArgsConstructor;
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
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final FoodTypeRepository foodTypeRepository;

    @Override
    public List<Food> findByFilter(LocalDate date, String food_name){
        // フィルタ条件によってDB検索を分岐
        boolean allType = (food_name == null || food_name.isEmpty() || "All".equals(food_name));

        // 条件なし
        if (date == null && allType) {
            return foodRepository.findAllOrderByDateDesc();
        }

        // 日付で絞り込み
        if (date != null && allType) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();
            return foodRepository.findByDateBetween(start, end);
        }

        // 食品名で絞り込み
        if (date == null) {
            return foodRepository.findByFoodType_FoodName(food_name);
        }

        // 日付＋食品名で絞り込み
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return foodRepository.findFoodByTypeAndDate(food_name, start, end);
    }

    @Override
    public List<Food> findByFilter(String dateStr, String food_name){
        LocalDate date = null;
        if (dateStr != null && !dateStr.isBlank()) {
            try {
                date = LocalDate.parse(dateStr);    // yyyy-MM-ddに
            } catch (DateTimeParseException e) {}
        }
        return findByFilter(date, food_name);
    }

    @Override
    public LinkedHashMap<YearMonth, List<Food>> groupByMonth(List<Food> food){
        // 月単位でグルーピング（Key は YearMonth）。表示は新しい月→古い月の順。
        return food.stream()
                .sorted(Comparator.comparing(Food::getDate).reversed())
                .collect(Collectors.groupingBy(
                        f -> YearMonth.from(f.getDate()),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public List<String> findAllTypeNames(){
        return foodTypeRepository.findAllFoodTypeNames();
    }

    @Override
    public Integer getCaloriesIntakeYesterday(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime start = yesterday.atStartOfDay();
        LocalDateTime end = yesterday.plusDays(1).atStartOfDay();
        return foodRepository
                .findByDateBetween(start, end)
                .stream()
                .mapToInt(Food::getKilocalories)
                .sum();
    }

    @Override
    @Transactional
    public void saveFoodRecord(Food food){
        foodRepository.save(food);
    }

    @Override
    @Transactional
    public void deleteFoodById(Long id){
        foodRepository.deleteById(id);
    }

    @Override
    public LinkedHashMap<LocalDate, Integer> getDailyIntakeForLast7Days(){
        // 過去7日間の日付範囲を計算
        LocalDate endDate = LocalDate.now();   // 今日
        LocalDate startDate = LocalDate.now().minusDays(7); // 7日前

        List<DailyIntakeCalorie> dailyIntakeList = foodRepository.sumCaloriesByDateBetween(startDate.atStartOfDay(), endDate.atStartOfDay());

        // 日付と合計カロリーをMapに格納
        LinkedHashMap<LocalDate, Integer> dailyIntakeMap = new LinkedHashMap<>();
        for (DailyIntakeCalorie dailyIntakeCalorie: dailyIntakeList) {
            LocalDate date = dailyIntakeCalorie.getDate();
            int calories = (int) dailyIntakeCalorie.getTotalIntakeCalories().longValue();
            dailyIntakeMap.put(date, calories);
        }
        return dailyIntakeMap;
    }
}
