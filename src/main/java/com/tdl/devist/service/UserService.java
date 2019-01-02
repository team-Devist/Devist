package com.tdl.devist.service;

import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User getUserByUserName(String name) {
        return userRepository.getOne(name);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}