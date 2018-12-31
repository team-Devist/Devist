package com.tdl.devist.repository;

import com.tdl.devist.model.DailyCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyCheckRepository extends JpaRepository<DailyCheck, Integer> {
}
