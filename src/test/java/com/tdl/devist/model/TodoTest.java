package com.tdl.devist.model;

import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void 변환_테스트_Byte에서_boolean_Array로() {
        Todo todo1 = generateTestTodoInstance();
        todo1.setRepeatDay((byte) 127);
        Assert.assertEquals(Arrays.toString(new boolean[]{true, true, true, true, true, true, true}), Arrays.toString(todo1.getRepeatCheckbox()));

        System.out.println(Arrays.toString(todo1.getRepeatCheckbox()));

        Todo todo2 = generateTestTodoInstance();
        todo2.setRepeatDay((byte) 65);
        Assert.assertEquals(Arrays.toString(new boolean[]{true, false, false, false, false, false, true}), Arrays.toString(todo2.getRepeatCheckbox()));
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