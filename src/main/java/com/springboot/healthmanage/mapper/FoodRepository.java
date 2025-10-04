package com.springboot.healthmanage.mapper;

import com.springboot.healthmanage.entity.Exercise;
import com.springboot.healthmanage.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    /**
     * 根据日期查询所有饮食记录
     */
    @Query("select f from Food f join fetch f.foodType t " +
            "where f.date >= :start and f.date < :end order by f.date desc")
    List<Food> findByDateBetween(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    /**
     * 根据食物名和日期查询记录
     */
    @Query("select f from Food f join fetch f.foodType t " +
            "where t.foodName = :foodName and f.date >= :start and f.date < :end " +
            "order by f.date desc")
    List<Food> findFoodByTypeAndDate(@Param("foodName") String foodName,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    /**
     * 根据食物名查询所有记录
     */
    List<Food> findByFoodType_FoodName(String food_name);

    /**
     * 查询所有饮食记录，并按日期排序
     */
    @Query("SELECT f FROM Food f ORDER BY f.date DESC")
    List<Food> findAllOrderByDateDesc();


}