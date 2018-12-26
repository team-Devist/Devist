package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author delf
 */
@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }
}
