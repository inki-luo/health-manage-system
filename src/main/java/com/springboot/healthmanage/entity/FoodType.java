package com.springboot.healthmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "food_names")
public class FoodType {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Getter
    @Column(name = "food_name", nullable = false, unique = true)
    private String foodName;

    @Override
    public String toString() {
        return this.foodName;
    }
}
