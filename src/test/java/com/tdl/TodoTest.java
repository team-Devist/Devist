package com.tdl;

import com.tdl.model.Todo;
import com.tdl.model.User;
import com.tdl.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testTodoCreate() {
        User user = new User();
        user.setId("delf");

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setTitle("DB 구현하기");

        todoRepository.save(todo);

        List<Todo> todoList = todoRepository.findAll();
        Assert.assertEquals(todoList.size(), 1);
        Assert.assertEquals(todoList.get(0).getTitle(), "DB 구현하기");
        Assert.assertEquals(todoList.get(0).getUser().getId(), "delf");
    }
}
