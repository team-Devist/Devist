package com.tdl.devist.service;

import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User getUserByUserName(String name) {
        return entityManager.find(User.class, name);
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }
}