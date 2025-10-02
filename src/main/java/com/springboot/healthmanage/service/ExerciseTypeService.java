package com.springboot.healthmanage.service;

import com.springboot.healthmanage.entity.ExerciseType;

import java.util.List;

public interface ExerciseTypeService {

    ExerciseType findExerciseTypeById(Integer exerciseTypeId);

    List<ExerciseType> findAllExerciseTypes();
    List<String> findAllExerciseTypeNames();
}
