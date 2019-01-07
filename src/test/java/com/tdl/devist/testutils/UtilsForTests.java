package com.tdl.devist.testutils;

import com.tdl.devist.model.DailyCheck;
import com.tdl.devist.model.Todo;
import com.tdl.devist.repository.DailyCheckRepository;
import com.tdl.devist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class UtilsForTests {
    @Autowired
    private DailyCheckRepository dailyCheckRepository;

    @Autowired
    private TodoRepository todoRepository;

    // todo의 dailyCheck 중에 planedDate가 2019-1-1 로 설정 된 것을 오늘 날짜로 변경한다.
    @Transactional
    public void updatePlanedDateToToday(int todoId) {
        Todo todo = todoRepository.getOne(todoId);
        DailyCheck dailyCheck = dailyCheckRepository.findByTodoAndPlanedDate(todo, LocalDate.of(2019, 1, 1)).get(0);
        dailyCheck.setPlanedDate(LocalDate.now());
        dailyCheckRepository.save(dailyCheck);
    }
}
