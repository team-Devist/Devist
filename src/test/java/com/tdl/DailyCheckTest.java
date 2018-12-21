package com.tdl;

import com.tdl.model.DailyCheck;
import com.tdl.model.Todo;
import com.tdl.model.User;
import com.tdl.repository.DailyCheckRepository;
import com.tdl.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author delf
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class DailyCheckTest {

    @Autowired
    private DailyCheckRepository dailyCheckRepository;
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testCreateDailyCheck() {
        User user = new User();
        user.setId("delf");

        Todo todo = new Todo();
        todo.setUser(user);

        todoRepository.save(todo);

        DailyCheck dc1 = new DailyCheck();
        dc1.setTodo(todo);
        Date d1 = new Date();
        dc1.setPlanedTime(d1);
        dc1.setDone(true);

        DailyCheck dc2 = new DailyCheck();
        dc2.setTodo(todo);
        Date d2 = new Date();
        dc2.setPlanedTime(d2);
        dc2.setDone(false);

        dailyCheckRepository.save(dc1);
        dailyCheckRepository.save(dc2);

        List<DailyCheck> todoList = dailyCheckRepository.findAll();
        Assert.assertEquals(todoList.size(), 2);

        Assert.assertEquals(todoList.get(0).getTodo().getId(), todo.getId());
        Assert.assertTrue(todoList.get(0).isDone());
        /*Date를 DB에저장할 때, String으로 변환하는 포멧이 다름
        Expected :1970-01-01 09:00:00.0
        Actual   :Thu Jan 01 09:00:00 KST 1970*/
        // Assert.assertEquals(todoList.get(0).getPlanedTime().toString(), d1.toString());

        Assert.assertEquals(todoList.get(1).getTodo().getId(), todo.getId());
        Assert.assertFalse(todoList.get(1).isDone());
        // Assert.assertEquals(todoList.get(1).getPlanedTime().toString(), d2.toString());
    }
}
