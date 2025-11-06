package com.springboot.healthmanage.mapper;

import com.springboot.healthmanage.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Integer> {

    /**
     * 获取所有运动类型，并按名称排序
     */
    // 全ての種別名（アルファベット順）
    @Query("SELECT t.exerciseTypeName FROM ExerciseType t ORDER BY t.exerciseTypeName asc")
    List<String>findAllExerciseTypeNames();

    List<ExerciseType> findAllByOrderByExerciseTypeNameAsc();


//    @Query("SELECT t FROM ExerciseType t WHERE t.exerciseTypeName == :typeName")
//    ExerciseType findByExerciseTypeName(@Param("typeName") String exerciseTypeName);
    /**
     * 根据运动类型名查询运动类型
     */
    Optional<ExerciseType> findByExerciseTypeName(@Param("exerciseTypeName") String exerciseTypeName);

}
