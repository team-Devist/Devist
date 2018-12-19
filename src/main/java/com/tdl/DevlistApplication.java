package com.tdl;

import com.tdl.model.User;
import com.tdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DevlistApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlistApplication.class, args);
    }
}
