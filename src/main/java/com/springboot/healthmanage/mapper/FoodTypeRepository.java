package com.springboot.healthmanage.mapper;

import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {

    /**
     * 获取所有食物名，并按名称排序
     */
    // 全ての種別名（アルファベット順）
    @Query("SELECT t.foodName FROM FoodType t ORDER BY t.foodName asc")
    List<String>findAllFoodTypeNames();

    List<FoodType> findAllByOrderByFoodNameAsc();
}
