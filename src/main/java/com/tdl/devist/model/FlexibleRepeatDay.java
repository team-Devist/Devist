package com.tdl.devist.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Entity
@ToString
@Setter
@Getter
public class FlexibleRepeatDay extends RepeatDay {
    private int doingCount = 1;
    private int weeksCount = 1;
    private int initDay = 1;

    @Override
    public boolean isTodaysTodo() {
        List<DailyCheck> list = todo.getDailyChecks();
        int doneCount = 0;
        int latestIdx = list.size() - 1;
        int cntIdx = latestIdx - LocalDateTime.now().getDayOfWeek().getValue() - initDay + 1;
        for (int i = latestIdx - 1; i > cntIdx; i--) {
            try {
                doneCount += list.get(i).isDone() ? 1 : 0;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return doneCount < doingCount;
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
}
