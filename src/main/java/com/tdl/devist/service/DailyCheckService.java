package com.tdl.devist.service;

import com.tdl.devist.model.DailyCheck;
import com.tdl.devist.model.Todo;
import com.tdl.devist.repository.DailyCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyCheckService {
    private final DailyCheckRepository dailyCheckRepository;

    @Autowired
    public DailyCheckService(DailyCheckRepository dailyCheckRepository) {
        this.dailyCheckRepository = dailyCheckRepository;
    }

    void createDailyCheckByTodo(Todo todo) {
        DailyCheck dailyCheck = new DailyCheck();
        dailyCheck.setTodo(todo);
        dailyCheck.setDone(todo.isDone());
        // 매일 자정에 실행 되면 현재 날짜는 실행과 동시에 하루가 지나게 되므로 이 전날의 날짜를 넣어준다.
        dailyCheck.setPlanedDate(LocalDate.now().minusDays(1));
        dailyCheckRepository.save(dailyCheck);
    }

    int getTotalCountByTodo(Todo todo) {
        return dailyCheckRepository.findByTodo(todo).size();
    }

    int getDoneCountByTodo(Todo todo) {
        return dailyCheckRepository.findByTodoAndIsDone(todo, true).size();
    }
}
