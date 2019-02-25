package com.tdl.devist.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@ToString
@Setter
@Getter
public class FlexibleRepeatDay extends RepeatDay {
    private int doingCount;
    private int weeksCount;
    private int initDay = 1;

    @Override
    public boolean isTodaysTodo() {
        return weeksCount > doingCount;
    }

    @Override
    public boolean isInitDay() {
        int today = LocalDate.now().getDayOfWeek().getValue();
        return today == initDay;
    }

    @Override
    public boolean initRepeatDay() {
        initDay = 1;
        return true;
    }

    public void toTodo() {
        doingCount++;
    }
}
