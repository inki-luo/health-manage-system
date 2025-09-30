package com.springboot.healthmanage.mapper;

import com.springboot.healthmanage.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {

    /**
     * 获取所有运动类型，并按名称排序
     */
//    @Query("SELECT etype FROM ExerciseType et ORDER BY etype.exerciseTypeName")
//    List<ExerciseType> findExerciseTypes();
//    List<ExerciseType> findAllByOrderByExerciseTypeNameAsc();

    // 全ての種別名（アルファベット順）
    @Query("SELECT t.exerciseTypeName FROM ExerciseType t ORDER BY t.exerciseTypeName asc")
    List<String>findAllExerciseTypeNames();

    List<ExerciseType> findAllByOrderByExerciseTypeNameAsc();
}
