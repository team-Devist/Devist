package com.tdl.devist;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class DevistApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevistApplication.class, args);
    }

}

