package com.tdl.devist.service;

import com.tdl.devist.model.DailyCheck;
import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.DailyCheckRepository;
import com.tdl.devist.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodoRepository todoRepository;
    private final DailyCheckRepository dailyCheckRepository;
    private final UserService userService;
    private final DailyCheckService dailyCheckService;

    @Autowired
    public TodoService(TodoRepository todoRepository, DailyCheckRepository dailyCheckRepository, UserService userservice, DailyCheckService dailyCheckService) {
        this.todoRepository = todoRepository;
        this.dailyCheckRepository = dailyCheckRepository;
        this.userService = userservice;
        this.dailyCheckService = dailyCheckService;
    }

    public void addTodo(String username, Todo todo) {
        User user = userService.getUserByUserName(username);
        todo.setUser(user);
        todo.setCreatedTime(LocalDateTime.now());
        todoRepository.save(todo);
        if (todo.isTodaysTodo()) {
            DailyCheck dailyCheck = dailyCheckService.createDailyCheckByTodo(todo);
            todo.setLatestDailyCheck(dailyCheck);
            todo.addDailyCheck(dailyCheck);
            todoRepository.save(todo);

            updateDoneRate(todo);
            userService.updateDoneRate(username);
        }
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

    public void updateTodo(int id, Todo editedTodo) {
        Todo originTodo = todoRepository.getOne(id);

        originTodo.setTitle(editedTodo.getTitle());
        originTodo.setDescription(editedTodo.getDescription());
        originTodo.setRepeatDay(editedTodo.getRepeatDay());
        todoRepository.save(originTodo);
    }

    public void renewTodos() {
        logger.info("Renew Todos");
        Set<User> userSet = new HashSet<>();
        for (Todo todo: todoRepository.findAll()) {
            // Todo: 같은 날에 두번 실행되지 않도록 하는 예외처리 추가하기.
            if (todo.isTodaysTodo()) {
                DailyCheck dailyCheck = dailyCheckService.createDailyCheckByTodo(todo);
                todo.setLatestDailyCheck(dailyCheck);
                todoRepository.save(todo);
                updateDoneRate(todo.getId());
                userSet.add(todo.getUser());
            }
        }

        for (User user: userSet)
            userService.updateDoneRate(user);
    }

    public void setTodoIsDone(int todoId, boolean isDone) {
        Todo todo = todoRepository.getOne(todoId);
        if (!todo.isTodaysTodo()) return; // Todo: Error 처리
        DailyCheck latestDailyCheck = todo.getLatestDailyCheck();
        latestDailyCheck.setDone(isDone);
        dailyCheckRepository.save(latestDailyCheck);
    }

    public void updateDoneRate(int todoId) {
        Todo todo = todoRepository.getOne(todoId);
        updateDoneRate(todo);
    }

    public void updateDoneRate(Todo todo) {
        int totalCount = dailyCheckService.getTotalCountByTodo(todo);
        int doneCount = dailyCheckService.getDoneCountByTodo(todo);

        todo.setDoneRate((double)doneCount / totalCount * 100.00);
        todoRepository.save(todo);

    }
}