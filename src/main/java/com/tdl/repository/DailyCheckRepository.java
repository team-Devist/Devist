package com.tdl.repository;

import com.tdl.model.DailyCheck;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author delf
 */
public interface DailyCheckRepository extends JpaRepository<DailyCheck, Integer> {
}
