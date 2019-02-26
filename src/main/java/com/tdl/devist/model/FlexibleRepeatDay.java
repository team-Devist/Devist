package com.tdl.devist.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@ToString
public class FlexibleRepeatDay extends RepeatDay {
    private int doingCount = 1;
    private int weeksCount = 1;
}
