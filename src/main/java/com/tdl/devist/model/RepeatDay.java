package com.tdl.devist.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "repeat_type")
public abstract class RepeatDay {
    @Id
    @GeneratedValue
    private int id;

    @MapsId
    @OneToOne(mappedBy = "repeatDay")
    @JoinColumn(name = "todo_id")
    protected Todo todo;

    public abstract boolean isTodaysTodo();
    public abstract boolean isInitDay();
    public abstract boolean initRepeatDay();
}
