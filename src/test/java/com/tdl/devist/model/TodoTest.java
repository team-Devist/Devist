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

/**
 * @author delf
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class TodoTest {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testTodoCreate() {
        User user = new User();
        user.setId("delf");

        Todo todo1 = new Todo();
        todo1.setUser(user);
        todo1.setTitle("DB 구현하기");

        user.addTodo(todo1);

        Todo todo2 = new Todo();
        todo2.setUser(user);
        todo2.setTitle("Security 구현하기");

        user.addTodo(todo2);

        userRepository.save(user);

        List<Todo> todoList = todoRepository.findAll();
        Assert.assertEquals(todoList.size(), 2);
        Assert.assertEquals(todoList.get(0).getUser().getId(), "delf");
        Assert.assertEquals(todoList.get(0).getTitle(), "DB 구현하기");
        Assert.assertEquals(todoList.get(1).getTitle(), "Security 구현하기");

        User resUser = userRepository.getOne("delf");
        List<Todo> todoList2 = resUser.getTodoList();
        Assert.assertEquals(todoList2.size(), 2);
        Assert.assertEquals(todoList2.get(0).getUser().getId(), "delf");
        Assert.assertEquals(todoList2.get(0).getTitle(), "DB 구현하기");
        Assert.assertEquals(todoList2.get(1).getTitle(), "Security 구현하기");

    }
}
