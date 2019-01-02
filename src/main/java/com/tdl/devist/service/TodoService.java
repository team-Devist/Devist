package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public void addTodo(User user, Todo todo) {
        todo.setUser(user);
        todo.setCreatedTime(LocalDateTime.now());
        user.addTodo(todo);
        todoRepository.save(todo);
    }

    @Transactional
    public Todo findTodoById(int id) {
        return todoRepository.getOne(id);
    }

    @Transactional
    public void deleteTodo(Todo todo) {
        todoRepository.delete(todo);
    }
}
