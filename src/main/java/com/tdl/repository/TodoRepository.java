package com.tdl.repository;

import com.tdl.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author delf
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
