package com.tdl.devist.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class FixedRepeatDay extends RepeatDay {
    @Column(length = 1)
    private byte dayOfWeek = 127;
}
