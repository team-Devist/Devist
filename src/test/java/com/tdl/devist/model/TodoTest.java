package com.tdl.devist.model;

import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class TodoTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    private final String TEST_USER_NAME = "my_name_is_user";
    private final String TEST_TODO_TITLE = "Todo 테스트하기 in TodoTest.class";

    @Test
    @Transactional
    public void EntityManager를_이용한_Todo_추가_테스트() {
        User user = generateTestUserInstance();

        userRepository.save(user);

        Todo todo1 = generateTestTodoInstance();
        user.addTodo(todo1);

        userRepository.save(user);

        User afterUser = userRepository.getOne(TEST_USER_NAME);
        List<Todo> todoList = afterUser.getTodoList();
        Assert.assertEquals(1, todoList.size());
        Todo afterTodo = todoList.get(0);
        Assert.assertEquals(TEST_TODO_TITLE, afterTodo.getTitle());
    }

    private User generateTestUserInstance() {
        User user = new User();
        user.setUsername(TEST_USER_NAME);
        user.setPassword("1234");
        return user;
    }

    private Todo generateTestTodoInstance() {
        Todo todo = new Todo();
        todo.setTitle(TEST_TODO_TITLE);
        return todo;
    }
}