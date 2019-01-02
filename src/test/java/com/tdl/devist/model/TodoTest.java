package com.tdl.devist.model;

import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class TodoTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    private final String TEST_USER_NAME = "my_name_is_user";
    private final String TEST_TODO_TITLE = "Todo 테스트하기";

    @Test
    public void EntityManager를_이용한_Todo_추가_테스트() {
        User user = generateTestUserInstance();

        userRepository.save(user);

        Todo todo1 = generateTestTodoInstance();
        user.addTodo(todo1);

        todoRepository.save(todo1);
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