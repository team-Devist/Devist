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
        System.out.println("@@ doingCount:" + doingCount);
        System.out.println("@@ weeksCount:" + weeksCount);
        List<DailyCheck> list = todo.getDailyChecks();
        System.out.println("@@ listsize" + list.size());
        int doneCount = 0;
        int latestIdx = list.size() - 1;
        int cntIdx = latestIdx - weeksCount * Calendar.DAY_OF_WEEK;

        for (int i = latestIdx - 1; i >= cntIdx; i--) {
            try {
                doneCount += list.get(i).isDone() ? 1 : 0;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        System.out.println("@@ donecount:" + doneCount);
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
