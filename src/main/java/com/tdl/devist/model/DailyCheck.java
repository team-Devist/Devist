package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class DailyCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime planedTime;
    private boolean isDone;

    @ManyToOne(cascade = CascadeType.ALL) // cascade 옵션 디사보기
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
