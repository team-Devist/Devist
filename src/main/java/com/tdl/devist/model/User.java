package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, length=200)
    private String password;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean enabled = true;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Authority> authorities = new ArrayList<>();

    @Column
    private String name;
    @Column(columnDefinition = "Decimal(10,2) default '100.00'")
    private double doneRate = 100.00;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Todo> todoList = new ArrayList<>();

    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }

    public boolean addTodo(Todo todo) {
        todo.convertRepeatDayBooleanArrToByte(); // 임시방편
        return todoList.add(todo);
    }

    public List<Todo> getTodayTodoList() {
        List<Todo> todayTodoList = new ArrayList<>();

        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        for (Todo todo: todoList)
            if (!todo.isDone() && ((todo.getRepeatDay() & (1 << (dayOfWeek - 1))) > 0))
                todayTodoList.add(todo);

        return todayTodoList;
    }

    public List<Todo> getUncompletedTodayTodoList() {
        List<Todo> uncompletedTodayTodoList = new ArrayList<>();

        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        for (Todo todo: todoList)
            if (todo.isDone() && ((todo.getRepeatDay() & (1 << (dayOfWeek - 1))) > 0))
                uncompletedTodayTodoList.add(todo);

        return uncompletedTodayTodoList;
    }

    public void editTodo(Todo beforeTodo, Todo afterTodo) {
        afterTodo.convertRepeatDayBooleanArrToByte(); // 임시방편
        todoList.set(indexOf(beforeTodo), afterTodo);
    }

    private int indexOf(Todo todo) {
        return todoList.indexOf(todo);
    }
}
