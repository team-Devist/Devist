package com.tdl.devist.model;

import com.tdl.devist.dto.TodoListDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

    public TodoListDto getTodoListDto() {
        return new TodoListDto(this.todoList);
    }

    private int indexOf(Todo todo) {
        return todoList.indexOf(todo);
    }
}