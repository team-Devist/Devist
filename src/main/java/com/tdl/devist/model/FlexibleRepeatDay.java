package com.tdl.devist.model;


import lombok.ToString;

import javax.persistence.Entity;

@Entity
@ToString
public class FlexibleRepeatDay extends RepeatDay {
    private int doingCount;
    private int weeksCount;
}
