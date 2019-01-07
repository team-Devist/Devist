package com.tdl.devist.service;

import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void TestUpdateDoneRate() {
        User user = userRepository.getOne("cjh5414");
        Assert.assertEquals(100.00, user.getDoneRate(), 00.01);

        userService.updateDoneRate("cjh5414");

        user = userRepository.getOne("cjh5414");
        Assert.assertEquals(66.66, user.getDoneRate(), 00.01);
    }
}
