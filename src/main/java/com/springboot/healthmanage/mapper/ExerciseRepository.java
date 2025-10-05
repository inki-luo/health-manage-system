package com.springboot.healthmanage.mapper;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    /**
     * 根据日期查询所有运动记录
     */
    @Query("select e from Exercise e join fetch e.exerciseType t " +
            "where e.date >= :start and e.date < :end order by e.date desc")
    List<Exercise> findByDateBetween(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    /**
     * 根据运动类型和日期查询记录
     */
    @Query("select e from Exercise e join fetch e.exerciseType t " +
            "where t.exerciseTypeName = :typeName and e.date >= :start and e.date < :end " +
            "order by e.date desc")
    List<Exercise> findExerciseByTypeAndDate(@Param("typeName") String typeName,
                                            @Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);

    /**
     * 根据运动类型查询所有记录
     */
    List<Exercise> findByExerciseType_ExerciseTypeName(String exercise_type_name);

    /**
     * 查询所有运动记录，并按日期排序
     */
    @Query("SELECT e FROM Exercise e ORDER BY e.date DESC")
    List<Exercise> findAllOrderByDateDesc();

    /**
     * 日付の降順で最新5件を取得
     */
    List<Exercise> findTop5ByOrderByDateDesc();
}
