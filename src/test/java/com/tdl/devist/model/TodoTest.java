package com.tdl.devist.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;


@Profile("dev")
public class TodoTest {

    private final String TEST_USER_NAME = "my_name_is_user";
    private final String TEST_TODO_TITLE = "Todo 테스트하기 in TodoTest.class";

    @Test
    public void 변환테스트_byte에서_booleanArr() {
        // TODO: 테스트 코드 작성
    }

    @Test
    public void 변환테스트_booleanArr에서_byte() {
        // TODO: 테스트 코드 작성
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