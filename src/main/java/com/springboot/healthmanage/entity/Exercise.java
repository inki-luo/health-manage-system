package com.springboot.healthmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Exercise {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private LocalDateTime date;

    @Getter
    @Setter
    private Integer calories;

    @Setter
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ExerciseType exerciseType;
    public ExerciseType getExerciseType() {
        return exerciseType;
    }


}
