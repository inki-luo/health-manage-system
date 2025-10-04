package com.springboot.healthmanage.service.impl;

import com.springboot.healthmanage.entity.FoodType;
import com.springboot.healthmanage.mapper.FoodTypeRepository;
import com.springboot.healthmanage.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTypeServiceImpl implements FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;

    @Override
    public FoodType findFoodTypeById(Integer foodTypeId){
        return foodTypeRepository.findById(foodTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food type id: " + foodTypeId));
    }

    @Override
    public List<FoodType> findAllFoodTypes(){
        return foodTypeRepository.findAllByOrderByFoodNameAsc();
    }

    @Override
    public List<String> findAllFoodNames(){
        return foodTypeRepository.findAllFoodTypeNames();
    }
}
