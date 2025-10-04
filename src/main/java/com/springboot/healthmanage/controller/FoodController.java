package com.springboot.healthmanage.controller;

import com.springboot.healthmanage.entity.Food;
import com.springboot.healthmanage.entity.FoodType;
import com.springboot.healthmanage.mapper.FoodRepository;
import com.springboot.healthmanage.mapper.FoodTypeRepository;
import com.springboot.healthmanage.service.FoodService;
import com.springboot.healthmanage.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodRepository foodRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final FoodService foodService;
    private final FoodTypeService foodTypeService;

    @ModelAttribute("Names")
    public Collection<FoodType> popularFoodNames() {
        return this.foodTypeRepository.findAllByOrderByFoodNameAsc();
    }

    // ===================== リスト一覧 =====================
    @GetMapping
    public String listFood(@RequestParam(required = false) String date,
                                @RequestParam(required = false, defaultValue = "All") String name,
                                ModelMap model) {
        List<Food> list = foodService.findByFilter(date, name);

        // 月ごとにグルーピング
        LinkedHashMap<?, List<Food>> grouped = foodService.groupByMonth(list);

        // 将分组结果放进模型
        model.addAttribute("foodByMonth", grouped);
        // 调用service拿到所有类型，用于下拉框选项
        model.addAttribute("foodNames", foodTypeService.findAllFoodNames());
        // 将用户选择的筛选条件放入model
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedType", name);

        return "/food/foodList";
    }

    // ===================== 追加 =====================
    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("food", new Food());
        // 下拉框需要的类型
        model.addAttribute("types", foodTypeService.findAllFoodTypes());
        return "/food/create";
    }

    @PostMapping("/new")
    public String processCreationForm(@ModelAttribute("food") Food food,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        // 检查卡路里大于0
        if (food.getKilocalories() != null && food.getKilocalories() <= 0) {
            result.rejectValue("kilocalories", "invalid", "Calories must be positive");
        }

        if (result.hasErrors()) {
            // エラー内容をログ出力
            result.getAllErrors().forEach(error -> {
                System.out.println("Binding Error: " + error.getObjectName() +
                        " - Field: " + (error instanceof FieldError ? ((FieldError) error).getField() : "N/A") +
                        " - Message: " + error.getDefaultMessage());
            });
            return "/food/create";
        }

        // フォームからのIDでFoodTypeエンティティをロード
        Integer typeIdFromForm = food.getFoodTypeId();
        // IDがnullでないことを確認
        if (typeIdFromForm  == null) {
            result.rejectValue("foodTypeId", "invalid", "Food Type must be selected");
            return "/food/create";
        }

        // データベースから完全な FoodType エンティティをロード
        FoodType fullFoodType = foodTypeService.findFoodTypeById(typeIdFromForm);
        if (fullFoodType == null) {
            // IDが見つからない場合のエラー処理 (通常は発生しないはず)
            result.rejectValue("foodType", "invalid", "Invalid Food Type selected");
            return "/food/create";
        }
        // ロードしたFoodTypeをFoodオブジェクトにセット
        food.setFoodType(fullFoodType);

        // 保存到数据库
        foodService.saveFoodRecord(food);
        redirectAttributes.addFlashAttribute("message", "A new food record has been added");
        return "redirect:/foods";
    }

    // ===================== 編集 =====================
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food Id:" + id));
        if (food.getFoodType() != null) {
            food.setFoodTypeId(food.getFoodType().getId());
        }

        model.addAttribute("food", food);
        model.addAttribute("types", foodTypeService.findAllFoodTypes());
        return "food/edit";
    }

    @PostMapping("/{id}/update")
    public String processUpdateForm(@PathVariable("id") Long id,
                                    @ModelAttribute Food recordFromForm,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // エラー内容をログ出力
            result.getAllErrors().forEach(error -> {
                System.out.println("Binding Error: " + error.getObjectName() +
                        " - Field: " + (error instanceof FieldError ? ((FieldError) error).getField() : "N/A") +
                        " - Message: " + error.getDefaultMessage());
            });
            return "/food/edit";
        }

        FoodType type = foodTypeRepository.findById(recordFromForm.getFoodTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid type Id"));

        //  DBから編集されるエンティティをロード
        Food recordToUpdate = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food Id:" + id));

        if (recordFromForm.getKilocalories() != null && recordFromForm.getKilocalories()<= 0) {
            result.rejectValue("kilocalories", "invalid", "Calories must be positive");
            return "/food/edit";
        }

        // エンティティのフィールドを更新
        recordToUpdate.setDate(recordFromForm.getDate());
        recordToUpdate.setKilocalories(recordFromForm.getKilocalories());
        recordToUpdate.setFoodType(type);

        foodService.saveFoodRecord(recordToUpdate); // 更新したエンティティを保存
        redirectAttributes.addFlashAttribute("message", "A Food record has been updated!");
        return "redirect:/foods";
    }

    // ===================== 削除 =====================
    @PostMapping("/{id}/delete")
    public String processDeleteRecord(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {

//        System.out.println("Attempting to delete food record with ID: " + id);

        foodService.deleteFoodById(id);

        redirectAttributes.addFlashAttribute("message", "The food record has been deleted!");
        return "redirect:/foods";
    }

}
