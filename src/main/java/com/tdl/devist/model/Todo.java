package com.tdl.devist.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private int id;
    private String title;
    private String content;
    private double doneRate;
    private byte repeatDay;
    private boolean isDone;
    private Date createdTime; // TODO: Date 클래스 말고 다른 클래스 사용 여지 있음

    // TODO: User와 Todo는 단방향? 양방향?
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
