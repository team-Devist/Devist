package com.tdl.devist.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author delf
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class TodoTest {

    //    @Autowired
//    private EntityManagerFactory entityManagerFactory;
    @PersistenceContext
    private EntityManager entityManager;


    private final String TEST_USER_NAME = "my_name_is_user";
    private final String TEST_TODO_TITLE = "Todo 테스트하기";

    @Test
    public void EntityManager를_이용한_Todo_추가_테스트() {
        User user = generateTestUserInstance();

        entityManager.persist(user);

        Todo todo1 = generateTestTodoInstance();
        user.addTodo(todo1);

        entityManager.persist(todo1);

        User findUser = entityManager.find(User.class, TEST_USER_NAME);
        Assert.assertEquals(1, findUser.getTodoList().size());
        Assert.assertEquals(TEST_TODO_TITLE, findUser.getTodoList().get(0).getTitle());
    }

    private User generateTestUserInstance() {
        User user = new User();
        user.setUsername(TEST_USER_NAME);
        user.setPassword("1234");
        return user;
    }

    private Todo generateTestTodoInstance() {
        Todo todo = new Todo();
        todo.setTitle(TEST_TODO_TITLE);
        return todo;
    }

}