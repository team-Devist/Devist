package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private int id;
    private String title;
    private String description;
    @Column(length = 1)
    private int repeatDay = 127;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDone = false;
    private LocalDateTime createdTime;
    private double doneRate = 0.0;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<DailyCheck> dailyChecks = new ArrayList<>();

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }
}
