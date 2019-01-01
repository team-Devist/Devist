package com.tdl.devist.service;


import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoServiceTests {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    private final String TEST_USER_NAME = "dbadmin";
    private final String TEST_TODO_TITLE = "Todo 테스트하기";

    private int entitySize;

    @Test
    @Transactional
    public void 순서1_서비스_레이어에서_Todo_추가_테스트() {
        generateAndSaveTestTodoInstance(TEST_USER_NAME);

        User targetUser = userService.getUserByUserName(TEST_USER_NAME);
        List<Todo> todoList = targetUser.getTodoList();
        entitySize = todoList.size();
        Assert.assertEquals(1, entitySize);
        Assert.assertEquals(TEST_TODO_TITLE, todoList.get(0).getTitle());
    }

    @Test
    @Transactional
    public void 순서2_서비스_레이어에서_Todo_삭제_테스트() {
        generateAndSaveTestTodoInstance(TEST_USER_NAME);
        User user = userService.getUserByUserName(TEST_USER_NAME);
        List<Todo> todoList = user.getTodoList();

        int todoId = todoList.get(0).getId();
        Todo todo = todoService.findTodoById(todoId);
        todoList.remove(todo);

        todoService.deleteTodo(todo);

        user = userService.getUserByUserName(TEST_USER_NAME);
        Assert.assertNotNull(user);
        todoList = user.getTodoList();
        Assert.assertEquals(0, todoList.size());
    }

    private Todo generateAndSaveTestTodoInstance(String username) {
        Todo todo = new Todo();
        User user = userService.getUserByUserName(username);
        todo.setTitle(TEST_TODO_TITLE);
        todoService.addTodo(user, todo);
        userService.updateUser(user);
        return todo;
    }
}
