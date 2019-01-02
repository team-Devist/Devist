package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author delf
 */
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public void addTodo(Todo todo) {
        todo.setCreatedTime(LocalDateTime.now());
        entityManager.persist(todo);
    }

//    public void getTodayTodoList() {
//        todoRepository.findAllByUsername();
//    }
}
