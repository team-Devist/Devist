package com.tdl.devist.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
// @DiscriminatorValue("flexible")
public class FlexibeRepeatDay extends RepeatDay {
    private int doingCount;
    private int weeksCount;
}
