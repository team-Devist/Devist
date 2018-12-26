package com.tdl.devist.service;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author delf
 */
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

    public List<Todo> getTodoListByUserName(String name) {
        return userRepository.getOne(name).getTodoList();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }


    public void addTodo(User user, Todo todo) {
        user.addTodo(todo);
        userRepository.save(user);
    }

}
