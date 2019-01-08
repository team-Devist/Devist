package com.tdl.devist.service;

import com.tdl.devist.model.DailyCheck;
import com.tdl.devist.model.Todo;
import com.tdl.devist.repository.DailyCheckRepository;
import com.tdl.devist.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class DailyCheckServiceTests {
    @Autowired
    private DailyCheckService dailyCheckService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private DailyCheckRepository dailyCheckRepository;

    @Test
    @Transactional
    public void TestCreateDailyCheckByTodo() {
        Todo todo = todoRepository.getOne(4);
        dailyCheckService.createDailyCheckByTodo(todo);
        DailyCheck dailyCheck = dailyCheckRepository.findByTodoAndPlanedDate(todo, LocalDate.now()).get(0);
        Assert.assertFalse(dailyCheck.isDone());
    }
}
