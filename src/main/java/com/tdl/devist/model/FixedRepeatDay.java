package com.tdl.devist.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

@Setter
@Getter
@Entity
@ToString
public class FixedRepeatDay extends RepeatDay {
    @Column(length = 1)
    private byte daysOfWeek = 127;
    @Transient
    private boolean[] checkboxs = {true, true, true, true, true, true, true};
    @Transient
    private final String[] WEEK_DAY_STR = {"월", "화", "수", "목", "금", "토", "일"};

    public void convertRepeatDayBooleanArrToByte() {
        daysOfWeek = 0;
        for (int i = 0; i < checkboxs.length; i++) {
            daysOfWeek |= checkboxs[i] ? (byte) (1 << (checkboxs.length - 1) - i) : 0;
        }
    }

    public void convertRepeatDayByteToBooleanArr() {
        for (int i = checkboxs.length - 1; i >= 0; i--) {
            checkboxs[checkboxs.length - 1 - i] = ((daysOfWeek >> i) & 1) == 1;
        }
    }

    @Override
    public boolean isTodaysTodo() {
        int today = LocalDate.now().getDayOfWeek().getValue();
        return (daysOfWeek & (1 << (today - 1))) > 0;

    }

    @Override
    public boolean isInitDay() {
        return false;
    }

    @Override
    public boolean initRepeatDay() {
        return false;
    }

    @Override
    public boolean isTodoOn(int w) {
        return (daysOfWeek & (1 << (w - 1))) > 0;
    }


}