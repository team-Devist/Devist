package com.tdl.devist.model;

import com.tdl.devist.repository.UserRepository;
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
        Assert.assertEquals( "delf", userList.get(0).getId());
        Assert.assertEquals( "pass123", userList.get(0).getPassword());
        Assert.assertEquals( "데르프", userList.get(0).getName());
        Assert.assertEquals( 0, Double.compare(userList.get(0).getDoneRate(), 80.00));

        Assert.assertEquals( "elena", userList.get(1).getId());
        Assert.assertEquals( "pass456", userList.get(1).getPassword());
        Assert.assertEquals( "엘레나", userList.get(1).getName());
        Assert.assertEquals( 0, Double.compare(userList.get(1).getDoneRate(), 81.00));
    }
}
