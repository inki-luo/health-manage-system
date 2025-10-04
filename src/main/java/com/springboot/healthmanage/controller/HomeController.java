package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.service.ExerciseService;
import com.springboot.healthmanage.service.FoodService;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.expression.Maps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        // ========== Past 7days Trend ==========
        Map<LocalDate, Integer> dailyIntake = foodService.getDailyIntakeForLast7Days();

        // グラフのラベル（日付 M/d形式）を生成
        List<String> dateLabels = dailyIntake.keySet().stream()
                .map(date -> date.format(DateTimeFormatter.ofPattern("M/d")))
                .toList();
        // グラフのデータ（カロリー）を生成
        List<Integer> intakeData = new ArrayList<>(dailyIntake.values());

        model.addAttribute("intakeData", intakeData);


        return "home"; // templates/home.html
    }
}
