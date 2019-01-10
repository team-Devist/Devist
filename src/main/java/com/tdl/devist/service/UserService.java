package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUserName(String name) {
        return userRepository.getOne(name);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void updateDoneRate(String username) {
        User user = userRepository.getOne(username);
        int doneCount = 0;
        int totalCount = 0;
        for (Todo todo : user.getTodoList()) {
            int dailyCheckCount = todo.getDailyChecks().size();
            totalCount += dailyCheckCount;
            doneCount += todo.getDoneRate() * dailyCheckCount / 100;
        }

        user.setDoneRate((double)doneCount / totalCount * 100.00);

        userRepository.save(user);
    }
}