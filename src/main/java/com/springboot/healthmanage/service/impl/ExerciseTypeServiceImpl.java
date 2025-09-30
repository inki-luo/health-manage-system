package com.springboot.healthmanage.service.impl;

import com.springboot.healthmanage.entity.ExerciseType;
import com.springboot.healthmanage.mapper.ExerciseTypeRepository;
import com.springboot.healthmanage.service.ExerciseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseTypeServiceImpl implements ExerciseTypeService {
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Override
    public ExerciseType findExerciseTypeById(Long exerciseTypeId) {

        return exerciseTypeRepository.findById(exerciseTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exercise type id: " + exerciseTypeId));
    }

    @Override
    public List<ExerciseType> findAllExerciseTypes() {
        // DB 登録済みの運動種別（アルファベット順）
        return exerciseTypeRepository.findAllByOrderByExerciseTypeNameAsc();
    }

    @Override
    public List<String> findAllExerciseTypeNames() {
        // DB 登録済みの運動種別名（アルファベット順）
        return exerciseTypeRepository.findAllExerciseTypeNames();
    }

}
