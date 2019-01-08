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
        dailyCheck.setDone(false);
        dailyCheck.setPlanedDate(LocalDate.now());
        dailyCheckRepository.save(dailyCheck);
    }

    int getTotalCountByTodo(Todo todo) {
        return dailyCheckRepository.findByTodo(todo).size();
    }

    int getDoneCountByTodo(Todo todo) {
        return dailyCheckRepository.findByTodoAndIsDone(todo, true).size();
    }

    void setTodayCheckDone(Todo todo, boolean isDone) {
        DailyCheck dailyCheck = dailyCheckRepository.findByTodoAndPlanedDate(todo, LocalDate.now()).get(0);
        dailyCheck.setDone(isDone);
        dailyCheckRepository.save(dailyCheck);
    }
}
