package com.tdl.devist.repository;

import com.tdl.devist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author delf
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
