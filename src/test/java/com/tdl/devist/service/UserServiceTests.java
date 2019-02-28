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

    @Autowired
    private TodoService todoService;

    @Test
    @Transactional
    // Note: 이 테스는은 import.sql에 종속된다
    public void TestUpdateDoneRate() {
        User user = userRepository.getOne("cjh5414");

        Assert.assertEquals(18.1, user.getDoneRate(), 00.1);

        todoService.setTodoIsDone(3, true);
        todoService.updateDoneRate(3);
        userService.updateDoneRate("cjh5414");

        user = userRepository.getOne("cjh5414");
        Assert.assertEquals(27.2, user.getDoneRate(), 00.1);
    }
}
