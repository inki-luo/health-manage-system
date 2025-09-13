package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.mapper.ExerciseRepository;
import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.mapper.ExerciseTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    private static final String VIEWS_EXERCISE_FORM = "createOrUpdateForm";

    private final ExerciseRepository exercises;
    private final ExerciseTypeRepository types;

    public ExerciseController(ExerciseRepository exercises, ExerciseTypeRepository types) {
        this.exercises = exercises;
        this.types = types;
    }

    /** 在模型中放入运动类型（下拉框用） */
    @ModelAttribute("types")
    public Collection<ExerciseType> popularExerciseTypes() {
        return this.types.findAllByOrderByExerciseTypeNameAsc();
    }

    /** 在模型中放入某条运动记录（编辑时用） */
    @ModelAttribute("exercise")
    public Exercise findExercise(@PathVariable(name = "exerciseId", required = false) Long exerciseId) {
        if (exerciseId == null) {
            return new Exercise();
        }
        return this.exercises.findExerciseById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found with id: " + exerciseId));
    }

//    显示运动记录列表
    @GetMapping
    public String listExercises(ModelMap model) {
        model.put("exercises", exercises.findAll()); // 查询所有记录
        return "list";  // 返回一个 Thymeleaf 模板
    }


    /** 处理新增提交 */
    @PostMapping("exercises/new")
    public String processCreationForm(Exercise exercise,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {

        // 校验卡路里必须大于 0
        if (exercise.getCalories() != null && exercise.getCalories() <= 0) {
            result.rejectValue("calories", "invalid", "Calories must be positive");
        }

        if (result.hasErrors()) {
            return VIEWS_EXERCISE_FORM;
        }

        this.exercises.save(exercise);
        redirectAttributes.addFlashAttribute("message", "New exercise has been added!");
        return "redirect:/exercises";
    }

    /** 处理编辑提交 */
    @PostMapping("/{exerciseId}/edit")
    public String processUpdateForm(Exercise exercise,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        // 校验卡路里
        if (exercise.getCalories() != null && exercise.getCalories() <= 0) {
            result.rejectValue("calories", "invalid", "Calories must be positive");
        }

        if (result.hasErrors()) {
            return VIEWS_EXERCISE_FORM;
        }

        this.exercises.save(exercise);
        redirectAttributes.addFlashAttribute("message", "Exercise has been updated!");
        return "redirect:/exercises";
    }

}
