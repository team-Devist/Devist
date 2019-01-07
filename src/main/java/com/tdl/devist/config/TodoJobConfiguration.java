package com.tdl.devist.config;

import com.tdl.devist.model.Todo;
import com.tdl.devist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration
public class TodoJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TodoService todoService;

    @Bean
    public Job creatingDailyChecksJob() {
        return jobBuilderFactory.get("creatingDailyChecksJob")
                .start(creatingDailyChecksStep())
                .build();
    }

    @Bean
    public Step creatingDailyChecksStep() {
        return stepBuilderFactory.get("creatingDailyChecksStep")
                .tasklet((contribution, chunkContext) -> {
                    todoService.renewTodos();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
