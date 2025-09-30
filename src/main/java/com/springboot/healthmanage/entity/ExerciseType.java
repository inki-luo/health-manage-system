package com.springboot.healthmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exercise_types")
public class ExerciseType{
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Getter
    @Column(name = "exercise_type_name", nullable = false, unique = true)
    private String exerciseTypeName;

    @Override
    public String toString() {
        return this.exerciseTypeName;
    }
}
