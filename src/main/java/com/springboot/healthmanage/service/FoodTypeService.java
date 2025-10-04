package com.springboot.healthmanage.service;

import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.entity.FoodType;

import java.util.List;

public interface FoodTypeService {
    FoodType findFoodTypeById(Integer foodTypeId);

    List<FoodType> findAllFoodTypes();
    List<String> findAllFoodNames();
}