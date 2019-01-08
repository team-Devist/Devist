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
    private final DailyCheckService dailyCheckService;

    @Autowired
    public TodoService(TodoRepository todoRepository, DailyCheckService dailyCheckService) {
        this.todoRepository = todoRepository;
        this.dailyCheckService = dailyCheckService;
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

    public void deleteTodo(User user, int todoId) {
        Todo todo = todoRepository.getOne(todoId);
        user.getTodoList().remove(todo);
        deleteTodo(todo);
    }

    public Todo editTodo(int id, Todo editedTodo) {
        Todo originTodo = todoRepository.getOne(id);
        User user = originTodo.getUser();
        return user.editTodo(originTodo, editedTodo);
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

    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void checkAndUpdateTodos() {

    public void renewTodos() {

        for (Todo todo: todoRepository.findAll()) {
            // Todo: 같은 날에 두번 실행되지 않도록 하는 예외처리 추가하기.
            if (todo.isTodaysTodo()) {
                dailyCheckService.createDailyCheckByTodo(todo);
                todo.setDone(false);
                todoRepository.save(todo);
            }
        }
    }

    public void setTodoIsDone(int todoId, boolean isDone) {
        Todo todo = todoRepository.getOne(todoId);
        if (!todo.isTodaysTodo()) return; // Todo: Error 처리
        todo.setDone(isDone);
        dailyCheckService.setTodayCheckDone(todo, isDone);
        todoRepository.save(todo);
    }

    public void updateDoneRate(int todoId) {
        Todo todo = todoRepository.getOne(todoId);
        int totalCount = dailyCheckService.getTotalCountByTodo(todo);
        int doneCount = dailyCheckService.getDoneCountByTodo(todo);

        todo.setDoneRate((double)doneCount / totalCount * 100.00);
        todoRepository.save(todo);
    }
}