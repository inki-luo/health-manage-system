package com.springboot.healthmanage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "food")
public class Food {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // フォームの形式に合わせる
    private LocalDateTime date;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "name_id")
    private FoodType foodType;

    @Setter
    @Getter
    private Integer kilocalories;

    // 【追加】 フォームからのIDを受け取るための一時的なフィールド
    @Transient // <--- JPAがこのフィールドを無視するように指示
    @Getter
    @Setter
    private Integer foodTypeId; // HTMLの name="foodTypeId" に対応
}
