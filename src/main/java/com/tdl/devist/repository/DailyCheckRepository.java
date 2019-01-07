package com.tdl.devist.repository;

import com.tdl.devist.model.DailyCheck;
import com.tdl.devist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyCheckRepository extends JpaRepository<DailyCheck, Integer> {
    List<DailyCheck> findByPlanedDate(LocalDate planedDate);
    List<DailyCheck> findByTodo(Todo todo);
    List<DailyCheck> findByTodoAndIsDone(Todo todo, boolean isDone);
}
