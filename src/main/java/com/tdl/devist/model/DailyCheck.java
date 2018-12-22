package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class DailyCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date planedTime;
    private boolean isDone;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
