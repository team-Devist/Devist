package com.tdl.devist.service;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.tdl.devist.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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