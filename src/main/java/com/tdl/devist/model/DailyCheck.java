package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "daily_checks")
@Setter
@Getter
@NoArgsConstructor
public class DailyCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_check_id")
    private int id;
    private LocalDate planedDate;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDone;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
