package com.tdl.devist.model;

import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testUserCreate() {
        User user1 = new User();
        user1.setUsername("delf");
        user1.setPassword("pass123");
        user1.setName("데르프");
        user1.setDoneRate(80.00);

        User user2 = new User();
        user2.setUsername("elena");
        user2.setPassword("pass456");
        user2.setName("엘레나");
        user2.setDoneRate(81.00);

        userRepository.save(user1);
        userRepository.save(user2);

        User saved_user1 = userRepository.getOne("delf");

        Assert.assertEquals("데르프", saved_user1.getName());
        Assert.assertEquals(0, Double.compare(saved_user1.getDoneRate(), 80.00));

        User saved_user2 = userRepository.getOne("elena");

        Assert.assertEquals("엘레나", saved_user2.getName());
        Assert.assertEquals(0, Double.compare(saved_user2.getDoneRate(), 81.00));
    }

    @Test
    @Transactional
    public void testGetTodayTodoList() {
        User user = userRepository.getOne("cjh5414");
        List<Todo> todoList = user.getTodayTodoList();

        int todoSize = 1;
        switch (LocalDate.now().getDayOfWeek().getValue()) {
            case 2:
            case 3:
            case 7:
                todoSize = 2;
                break;
        }

        Assert.assertEquals(todoSize, todoList.size());
    }

    @Test
    @Transactional
    public void testGetCompletedTodayTodoList() {
        User user = userRepository.getOne("cjh5414");
        List<Todo> todoList = user.getCompletedTodayTodoList();

        Assert.assertEquals(2, todoList.size());
        Assert.assertEquals("완료된 할 일", todoList.get(0).getTitle());
    }
}

