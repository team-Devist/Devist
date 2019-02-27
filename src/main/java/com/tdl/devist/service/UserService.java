package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


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
        updateDoneRate(user);
    }

    // Must be called after TodoService.updateDoneRate()
    // Todo: updateDoneRate 이 수행되기 전에 호출돼서 todo의 doneRate 이 업데이트 된 상태가 아니라면 예외처리 해주는 코드 작성.
    public void updateDoneRate(User user) {
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

    public boolean hasAuthorization(String userName, Todo todo) {
        return !todo.getUser().getUsername().equals(userName);
    }
}