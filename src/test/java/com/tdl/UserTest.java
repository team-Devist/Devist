package com.tdl;

import com.tdl.model.User;
import com.tdl.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author delf
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserCreate() {
        User user1 = new User();
        user1.setId("delf");
        user1.setPassword("pass123");
        user1.setName("데르프");
        user1.setDoneRate(80.00);

        User user2 = new User();
        user2.setId("elena");
        user2.setPassword("pass456");
        user2.setName("엘레나");
        user2.setDoneRate(81.00);

        userRepository.save(user1);
        userRepository.save(user2);
        List<User> userList= userRepository.findAll();


        Assert.assertEquals(userList.size(), 2);
        Assert.assertEquals(userList.get(0).getId(), "delf");
        Assert.assertEquals(userList.get(0).getPassword(), "pass123");
        Assert.assertEquals(userList.get(0).getName(), "데르프");
        Assert.assertEquals(Double.compare(userList.get(0).getDoneRate(), 80.00), 0);

        Assert.assertEquals(userList.get(1).getId(), "elena");
        Assert.assertEquals(userList.get(1).getPassword(), "pass456");
        Assert.assertEquals(userList.get(1).getName(), "엘레나");
        Assert.assertEquals(Double.compare(userList.get(1).getDoneRate(), 81.00), 0);
        // Assert.assertEquals(userList.get(1).getDoneRate(), 81.00);

    }
}
