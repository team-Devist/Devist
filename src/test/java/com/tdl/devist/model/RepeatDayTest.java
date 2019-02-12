package com.tdl.devist.model;

import com.tdl.devist.repository.FixedRepeatDayRepository;
import com.tdl.devist.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class RepeatDayTest {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private FixedRepeatDayRepository fixedRepeatDayRepository;

    @Test
    public void testFixedRepeatDay() {
        Todo todo = new Todo();
        todo.setTitle("test todo");
        todo.setDescription("test todo desc");

        int originSize = fixedRepeatDayRepository.findAll().size();

        FixedRepeatDay fixedRepeatDay = new FixedRepeatDay();
        todo.setRepeatDay(fixedRepeatDay);
        fixedRepeatDay.setTodo(todo);

        todoRepository.save(todo);

        List<FixedRepeatDay> fixedRepeatDays = fixedRepeatDayRepository.findAll();
        assertEquals(originSize + 1, fixedRepeatDays.size());
   }
}
