package com.springboot.healthmanage.service;

import com.springboot.healthmanage.entity.ExerciseType;

import java.util.List;
import java.util.Optional;

public interface ExerciseTypeService {

    ExerciseType findExerciseTypeById(Integer exerciseTypeId);

    List<ExerciseType> findAllExerciseTypes();
    List<String> findAllExerciseTypeNames();

    ExerciseType findOrCreateExerciseTypeByName(String exerciseTypeName);
}
