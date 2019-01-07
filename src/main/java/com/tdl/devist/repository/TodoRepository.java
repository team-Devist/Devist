package com.tdl.devist.repository;

import com.tdl.devist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByTitle(String title);
}
