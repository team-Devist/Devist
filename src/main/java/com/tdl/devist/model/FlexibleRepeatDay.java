package com.tdl.devist.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public boolean isOnToday() {
        return isOn(LocalDateTime.now().getDayOfWeek().getValue());
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

    @Override
    public boolean isOn(int w) {
        List<DailyCheck> list = todo.getDailyChecks();
        int doneCount = 0;
        int latestIdx = list.size() - 1;
        int cntIdx = latestIdx - (w - initDay);

        if (latestIdx - cntIdx < doneCount) {
            return true;
        }

        for (int i = latestIdx - 1; i >= cntIdx; i--) {
            try {
                doneCount += list.get(i).isDone() ? 1 : 0;
                if(doneCount > doingCount) return false;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return doneCount < doingCount;
    }
}