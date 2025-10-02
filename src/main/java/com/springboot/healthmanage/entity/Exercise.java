package com.springboot.healthmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise")
public class Exercise {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // フォームの形式に合わせる
    private LocalDateTime date;

    @Getter
    @Setter
    private Integer kilocalories;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ExerciseType exerciseType;

    // 【追加】 フォームからのIDを受け取るための一時的なフィールド
    @Transient // <--- JPAがこのフィールドを無視するように指示
    @Getter
    @Setter
    private Integer exerciseTypeId; // HTMLの name="exerciseTypeId" に対応
}
