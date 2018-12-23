package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
    // TODO: 각 입력 값에 대한 길이 제한 추가
    @Id
    @Column(name = "user_name")
    private String id;
    private String password;
    private String name;

    private double doneRate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();

    public boolean addTodo(Todo todo) {
        return todoList.add(todo);
    }
}
