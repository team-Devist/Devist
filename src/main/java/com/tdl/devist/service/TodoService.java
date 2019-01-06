package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final DailyCheckService dailyCheckService;

    @Autowired
    public TodoService(TodoRepository todoRepository, DailyCheckService dailyCheckService) {
        this.todoRepository = todoRepository;
        this.dailyCheckService = dailyCheckService;
    }

    public void addTodo(User user, Todo todo) {
        todo.convertRepeatDayBooleanArrToByte(); // stop-gap
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

    public void setTodoIsDone(int todo_id, boolean isDone) {
        Todo todo = todoRepository.getOne(todo_id);
        todo.setDone(isDone);
        todoRepository.save(todo);
    }

    public void checkAndUpdateTodos() {
        int dayOfWeek = 1 << (LocalDate.now().getDayOfWeek().getValue() - 1);
        for (Todo todo: todoRepository.findAll()) {
            if ((todo.getRepeatDay() & dayOfWeek) > 0) {
                dailyCheckService.createDailyCheckByTodo(todo);
                todo.setDone(false);
            }
        }
    }
}