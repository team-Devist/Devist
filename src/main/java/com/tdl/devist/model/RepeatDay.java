package com.tdl.devist.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "repeat_type")
abstract class RepeatDay {
    @Id
    @GeneratedValue
    private int id;
}
