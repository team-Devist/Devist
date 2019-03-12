package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private int id;
    private String title;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RepeatDay repeatDay;
    private LocalDateTime createdTime;
    private double doneRate = 0.0;
    @OneToOne
    @JoinColumn(name = "latest_daily_check_id")
    private DailyCheck latestDailyCheck;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<DailyCheck> dailyChecks = new ArrayList<>();

    public enum Type {
        FIXED, FLEXIBLE, TYPE_ERROR;
    }

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }

    public boolean isDone() {
        if (latestDailyCheck == null) {
            // Todo: latestDaliyCheck 가 없다는 로그 출력
            return false;
        }
        return latestDailyCheck.isDone();
    }

    public boolean isTodaysTodo() {
        return repeatDay.isTodaysTodo();
    }

    public boolean isInitDay() {
        return repeatDay.isInitDay();
    }

    public boolean initRepeatDay() {
        return repeatDay.initRepeatDay();
    }

    public Type getType() {
        if (this.repeatDay instanceof FixedRepeatDay) return Type.FIXED;
        else if (this.repeatDay instanceof FlexibleRepeatDay) return Type.FLEXIBLE;
        return Type.TYPE_ERROR;
    }
}
