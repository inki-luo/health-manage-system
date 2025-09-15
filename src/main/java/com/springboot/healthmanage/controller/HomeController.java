package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.service.ExerciseService;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ExerciseService exerciseService;
//    private final FoodService foodService;

//    public HomeController(ExerciseService exerciseService, FoodService foodService) {
    public HomeController(ExerciseService exerciseService) {

    this.exerciseService = exerciseService;
//        this.foodService = foodService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        int burned = exerciseService.getCaloriesBurnedYesterday();
//        int intake = foodService.getCaloriesIntakeYesterday();
//        int diff = intake - burned; // 正数=盈余，负数=缺口

        model.addAttribute("burned", burned);
//        model.addAttribute("intake", intake);
//        model.addAttribute("diff", diff);

        return "home"; // templates/home.html
    }
}
