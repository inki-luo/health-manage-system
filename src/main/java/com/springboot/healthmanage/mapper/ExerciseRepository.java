package com.springboot.healthmanage.mapper;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    /**
     * 根据日期查询所有运动记录
     */
    List<Exercise> findExerciseByDate(LocalDateTime date);

    /**
     * 根据运动类型查询所有记录
     */
    List<Exercise> findExerciseByExerciseType(ExerciseType exerciseType);

//    根据ExerciseId查询运动记录
    Optional<Exercise> findExerciseById(Long id);

    /**
     * 查询所有运动记录，并按日期排序
     */
    @Query("SELECT e FROM Exercise e ORDER BY e.date DESC")
    List<Exercise> findAllOrderByDateDesc();
}
