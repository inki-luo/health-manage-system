package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.mapper.ExerciseTypeRepository;
import com.springboot.healthmanage.service.ExerciseService;
import com.springboot.healthmanage.service.ExerciseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.util.*;

@Controller
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseTypeService exerciseTypeService;

    /** 在模型中放入运动类型（下拉框用） */
    @ModelAttribute("types")
    public Collection<ExerciseType> popularExerciseTypes() {
        return this.exerciseTypeRepository.findAllByOrderByExerciseTypeNameAsc();
    }

    // ===================== 列表页 =====================
    //    显示运动记录列表
    @GetMapping
    public String listExercises(@RequestParam(required = false) String date,
                                    @RequestParam(required = false, defaultValue = "All") String type,
                                    ModelMap model) {
        // 从 Service 获取过滤后的记录
        List<Exercise> list = exerciseService.findByFilter(date, type);

        // 按月份分组
        LinkedHashMap<?, List<Exercise>> grouped = exerciseService.groupByMonth(list);

        // 生成汇总信息：每月运动次数 & 消耗
        Map<YearMonth, Map<String, Object>> summaries = new LinkedHashMap<>();
        grouped.forEach((ym, exercises) -> {
            int count = exercises.size();
            int totalCalories = exercises.stream()
                    .mapToInt(e -> e.getKilocalories() == null ? 0 : e.getKilocalories())
                    .sum();

            Map<String, Object> summary = new HashMap<>();
            summary.put("count", count);
            summary.put("totalCalories", totalCalories);
            summaries.put((YearMonth) ym, summary);
        });

        // 将分组结果放进模型
        model.addAttribute("exercisesByMonth", grouped);
        // 将月份运动类型总结放进模型
        model.addAttribute("summaries", summaries);
        // 调用service拿到所有类型，用于下拉框选项
        model.addAttribute("exerciseTypes", exerciseTypeService.findAllExerciseTypeNames());
        // 将用户选择的筛选条件放入model
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedType", type);

        return "/exercises/exerciseList";  // 返回一个 Thymeleaf 模板
    }

    // ===================== 新增 =====================
    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        // 下拉框需要的类型
        model.addAttribute("types", exerciseTypeService.findAllExerciseTypes());
        return "/exercises/createOrUpdateForm";
    }

    @PostMapping("/new")
    public String processCreationForm(@ModelAttribute("exercise") Exercise exercise,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        // 检查卡路里大于0
        if (exercise.getKilocalories() != null && exercise.getKilocalories() <= 0) {
            result.rejectValue("kilocalories", "invalid", "Calories must be positive");
        }

        if (result.hasErrors()) {
            // エラー内容をログ出力
            result.getAllErrors().forEach(error -> {
                System.out.println("Binding Error: " + error.getObjectName() +
                        " - Field: " + (error instanceof FieldError ? ((FieldError) error).getField() : "N/A") +
                        " - Message: " + error.getDefaultMessage());
            });
            return "/exercises/createOrUpdateForm";
        }

        // フォームからのIDでExerciseTypeエンティティをロード
        Integer typeIdFromForm = exercise.getExerciseTypeId();
        // IDがnullでないことを確認
        if (typeIdFromForm  == null) {
            result.rejectValue("exerciseTypeId", "invalid", "Exercise Type must be selected");
            return "/exercises/createOrUpdateForm";
        }

        // データベースから完全な ExerciseType エンティティをロード
        ExerciseType fullExerciseType = exerciseTypeService.findExerciseTypeById(typeIdFromForm);
        if (fullExerciseType == null) {
            // IDが見つからない場合のエラー処理 (通常は発生しないはず)
            result.rejectValue("exerciseType", "invalid", "Invalid Exercise Type selected");
            return "/exercises/createOrUpdateForm";
        }
        // ロードしたExerciseTypeをExerciseオブジェクトにセット
        exercise.setExerciseType(fullExerciseType);

        // 保存到数据库
        exerciseService.saveExerciseRecord(exercise);
        redirectAttributes.addFlashAttribute("message", "A new exercise record has been added");
        return "redirect:/exercises";
    }
//
//    // ===================== 编辑 =====================
//    @GetMapping("/{exerciseId}/edit")
//    public String initUpdateForm(@PathVariable("exerciseId") Long exerciseId, Model model) {
//        Exercise exercise = exerciseService.findById(exerciseId);
//        model.addAttribute("exercise", exercise);
//        return VIEWS_EXERCISE_FORM;
//    }
//
//    @PostMapping("/{exerciseId}/edit")
//    public String processUpdateForm(@ModelAttribute("exercise") Exercise exercise,
//                                    BindingResult result,
//                                    RedirectAttributes redirectAttributes) {
//
//        if (exercise.getCalories() != null && exercise.getCalories() <= 0) {
//            result.rejectValue("calories", "invalid", "Calories must be positive");
//        }
//
//        if (result.hasErrors()) {
//            return VIEWS_EXERCISE_FORM;
//        }
//
//        exerciseService.save(exercise);
//        redirectAttributes.addFlashAttribute("message", "Exercise has been updated!");
//        return "redirect:/exercises";
//    }

//
//    /** 处理编辑提交 */
//    @PostMapping("/{exerciseId}/edit")
//    public String processUpdateForm(Exercise exercise,
//                                    BindingResult result,
//                                    RedirectAttributes redirectAttributes) {
//
//        // 校验卡路里
//        if (exercise.getCalories() != null && exercise.getCalories() <= 0) {
//            result.rejectValue("calories", "invalid", "Calories must be positive");
//        }
//
//        if (result.hasErrors()) {
//            return VIEWS_EXERCISE_FORM;
//        }
//
//        this.exercises.save(exercise);
//        redirectAttributes.addFlashAttribute("message", "Exercise has been updated!");
//        return "redirect:/exercises";
//    }

}
