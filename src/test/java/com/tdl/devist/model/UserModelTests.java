package com.tdl.devist.model;


import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class UserModelTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testDefaultAdminUser() {
        User admin_user = userRepository.getOne("admin");
        Assert.assertEquals(admin_user.getUsername(), "admin");

        User dba_user= userRepository.getOne("dbadmin");
        Assert.assertEquals(dba_user.getUsername(), "dbadmin");
    }
}
