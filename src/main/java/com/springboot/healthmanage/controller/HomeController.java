package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.service.ExerciseService;
import com.springboot.healthmanage.service.FoodService;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.expression.Maps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ExerciseService exerciseService;
    private final FoodService foodService;

    public HomeController(ExerciseService exerciseService, FoodService foodService) {
        this.exerciseService = exerciseService;
        this.foodService = foodService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        // ========== Yesterday Summary ==========
        int burned = exerciseService.getCaloriesBurnedYesterday();
        int intake = foodService.getCaloriesIntakeYesterday();
        int bmr = 1419;
        int totalBurned = burned + bmr;
        int diff = intake - totalBurned;    // diff<0 --> calorie deficit
        model.addAttribute("burned", burned);
        model.addAttribute("intake", intake);
        model.addAttribute("bmr", bmr);
        model.addAttribute("diff", diff);

        // ========== Past 7 day Trend ==========
        LinkedHashMap<LocalDate, Integer> dailyIntakeMap = foodService.getDailyIntakeForLast7Days();
        LinkedHashMap<LocalDate, Integer> dailyBurnedMap = exerciseService.getDailyBurnedForLast7Days();

        // 過去7日間の日付リストを生成 --> グラフのX軸
        List<LocalDate> last7Days = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            last7Days.add(LocalDate.now().minusDays(i));
        }

        // Viewに渡すためのリストを生成
        List<String> dateLabels = new ArrayList<>();
        List<Integer> intakeData = new ArrayList<>();
        List<Integer> burnedData = new ArrayList<>();
        List<Integer> diffData = new ArrayList<>();
        List<Boolean> goalAchievedData = new ArrayList<>();

        // 7日間の日付をループし、2つのリストにデータを振り分ける
        for (LocalDate date : last7Days) {
            // ラベルを追加
            dateLabels.add(date.format(DateTimeFormatter.ofPattern("M/d")));
            // データを追加
            int dailyIntake = dailyIntakeMap.getOrDefault(date, 0);
            intakeData.add(dailyIntake);
            int dailyBurned = dailyBurnedMap.getOrDefault(date, 0) + bmr;
            burnedData.add(dailyBurned);
            int dailyBmr = ThreadLocalRandom.current().nextInt(1370, 1430);
            int dailyDiff = dailyIntake - dailyBurned;
            diffData.add(dailyDiff);
            goalAchievedData.add(dailyDiff < 0);

        }

        model.addAttribute("dateLabels", dateLabels);
        model.addAttribute("intakeData", intakeData);
        model.addAttribute("burnedData", burnedData);
        model.addAttribute("goalAchievedData", goalAchievedData);
        model.addAttribute("diffData", diffData);

        return "home"; // templates/home.html
    }
}
