package com.tdl.devist.repository;

import com.tdl.devist.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestModelRepository extends JpaRepository<TestModel, Integer> {
}
