package com.tdl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String content;
    private double doneRate;
    private byte repeatDay;
    private boolean isDone;
    private Date createdTime;

    // TODO: User와 Todo는 단방향? 양방향?
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
}
