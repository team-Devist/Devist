package com.tdl.devist.model;


import javax.persistence.Entity;

@Entity
public class FlexibleRepeatDay extends RepeatDay {
    private int doingCount;
    private int weeksCount;
}
