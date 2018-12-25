package com.tdl.devist.model;

import com.tdl.devist.repository.DailyCheckRepository;
import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author delf
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class DailyCheckTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    public void testCreateDailyCheck() {
        User user = new User();
        user.setUsername("delf");
        user.setPassword("1234");

        Todo todo = new Todo();
        todo.setUser(user);

        DailyCheck dc1 = new DailyCheck();
        dc1.setTodo(todo);
        LocalDateTime d1 = LocalDateTime.now();
        dc1.setPlanedTime(d1);
        dc1.setDone(true);

        todo.addDailyCheck(dc1);

        DailyCheck dc2 = new DailyCheck();
        dc2.setTodo(todo);
        LocalDateTime d2 = LocalDateTime.now();
        dc2.setPlanedTime(d2);
        dc2.setDone(false);

        todo.addDailyCheck(dc2);

        todoRepository.save(todo);

        List<DailyCheck> dailyChecks = todoRepository.getOne(todo.getId()).getDailyChecks();
        Assert.assertEquals(2, dailyChecks.size());

        Assert.assertEquals(dc1.getId(), dailyChecks.get(0).getId());
        Assert.assertEquals(dc1.isDone(), dailyChecks.get(0).isDone());
        Assert.assertEquals(dc1.getPlanedTime(), dailyChecks.get(0).getPlanedTime());

        Assert.assertEquals(dc2.getId(), dailyChecks.get(1).getId());
        Assert.assertEquals(dc2.isDone(), dailyChecks.get(1).isDone());
        Assert.assertEquals(dc2.getPlanedTime(), dailyChecks.get(1).getPlanedTime());

        Todo resTodo = todoRepository.getOne(todo.getId());
        List<DailyCheck> set = resTodo.getDailyChecks();
        Assert.assertTrue(set.contains(dc1));
        Assert.assertTrue(set.contains(dc2));
    }
}
