package com.tdl.devist.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Setter
@Getter
@Entity
@ToString
public class FixedRepeatDay extends RepeatDay {
    @Column(length = 1)
    private byte daysOfWeek = 127;

    @Transient
    private Week checkbox;
    @Transient
    private boolean[] checkboxs = {true, false, true, true, true, true, true};
    @Transient
    private final String[] WEEK_DAY_STR = {"일", "월", "화", "수", "목", "금", "토"};

    public void convertRepeatDayBooleanArrToByte() {
        daysOfWeek = 0;
        for (int i = checkboxs.length - 1; i >= 0; i--) {
            daysOfWeek |= checkboxs[i] ? (byte) (1 << (checkboxs.length - 1) - i) : 0;
        }
    }

    public void convertRepeatDayByteToBooleanArr() {
        for (int i = 0; i < checkboxs.length; i++) {
            checkboxs[checkboxs.length - 1 - i] = ((daysOfWeek >> i) & 1) == 1;
        }
    }

//    public void convertRepeatDayBooleanArrToByte() {
//        daysOfWeek = checkbox.getByte();
//    }
//
//    public void convertRepeatDayByteToBooleanArr() {
//        checkbox.setCheckboxs(daysOfWeek);
//    }
}