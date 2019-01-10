package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "daily_checks")
@Setter
@Getter
@NoArgsConstructor
public class DailyCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate planedDate;
    private boolean isDone;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
