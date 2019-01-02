package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void addTodo(User user, Todo todo) {
        todo.setUser(user);
        todo.setCreatedTime(LocalDateTime.now());
        user.addTodo(todo);
        todoRepository.save(todo);
    }

    public Todo findTodoById(int id) {
        return todoRepository.getOne(id);
    }

    public void deleteTodo(Todo todo) {
        todoRepository.delete(todo);
    }

    public long count() {
        return todoRepository.count();
    }
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

}