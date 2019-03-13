package com.tdl.devist.dto;

import com.tdl.devist.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoListDto {
    private List<Todo> notDoneFixedTodoList = new ArrayList<>();
    private List<Todo> doneFixedTodoList = new ArrayList<>();
    private List<Todo> notDoneFlexibleTodoList = new ArrayList<>();
    private List<Todo> doneFlexibleTodoList= new ArrayList<>();
    public TodoListDto(List<Todo> todoList) {
        for (Todo todo: todoList) {
            if (todo.getType() == Todo.Type.FIXED) {
                if (todo.isDone()) doneFixedTodoList.add(todo);
                else notDoneFixedTodoList.add(todo);
            }

            else if (todo.getType() == Todo.Type.FLEXIBLE) {
                if (todo.isDone()) doneFlexibleTodoList.add(todo);
                else notDoneFlexibleTodoList.add(todo);
            }
        }
    }

    public List<Todo> getNotDoneTodayFixedTodoList() {
        List<Todo> res = new ArrayList<>();
        for (Todo todo : notDoneFixedTodoList)
            if (todo.isOnToday())
                res.add(todo);

        return res;
    }

    public List<Todo> getDoneTodayFixedTodoList() {
        List<Todo> res = new ArrayList<>();
        for (Todo todo : doneFixedTodoList)
            if (todo.isOnToday())
                res.add(todo);

        return res;
    }

    public List<Todo> getNotDoneTodayFlexibleTodoList() {
        List<Todo> res = new ArrayList<>();
        for (Todo todo : notDoneFlexibleTodoList)
            if (todo.isOnToday())
                res.add(todo);

        return res;
    }

    public List<Todo> getDoneTodayFlexibleTodoList() {
        List<Todo> res = new ArrayList<>();
        for (Todo todo : doneFlexibleTodoList)
            if (todo.isOnToday())
                res.add(todo);

        return res;
    }
}
