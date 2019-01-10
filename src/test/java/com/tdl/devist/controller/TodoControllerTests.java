package com.tdl.devist.controller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.testutils.UtilsForTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
@ContextConfiguration
@WebAppConfiguration
public class TodoControllerTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UtilsForTests utils;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetTodoListPage() throws Exception {
        mockMvc.perform(get("/todo")
                .with(user("admin").password("1234").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTodoAddForm() throws Exception {
        mockMvc.perform(get("/todo/add")
                .with(user("admin").password("1234").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testAddTodo() throws Exception {
        User user = userRepository.getOne("admin");
        int size = user.getTodoList().size();
        Assert.assertEquals(0, size);

        mockMvc.perform(post("/todo/add")
                .with(user("admin").password("1234").roles("USER", "ADMIN"))
                .param("title", "test title")
                .param("description", "test description")
                .param("repeatDay", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        User afterUser = userRepository.getOne("admin");
        Assert.assertEquals(size + 1, afterUser.getTodoList().size());
        Assert.assertEquals("test title", afterUser.getTodoList().get(0).getTitle());
    }

    @Test
    @Transactional
    public void testDeleteTodo() throws Exception {
        User user = userRepository.getOne("cjh5414");
        List<Todo> todoList = user.getTodoList();
        int size = todoList.size();
        Assert.assertNotEquals(0, size);
        int todoId = todoList.get(0).getId();

        mockMvc.perform(post("/todo/" + todoId + "/delete")
                .with(user("cjh5414").password("1234").roles("USER"))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        User afterUSer = userRepository.getOne("cjh5414");
        Assert.assertEquals(size - 1, afterUSer.getTodoList().size());
    }

    @Test
    @Transactional
    public void testEditTodo() throws Exception {
        User user = userRepository.getOne("cjh5414");
        List<Todo> todoList = user.getTodoList();
        Assert.assertNotEquals("TodoList가 비어있음.", 0, todoList.size());
        int todoId = todoList.get(0).getId();

        String editedTitle = "바뀐 타이틀";
        mockMvc.perform(post("/todo/" + todoId + "/edit")
                .with(user("cjh5414").password("1234").roles("USER"))
                .param("title", editedTitle)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        User afterUSer = userRepository.getOne("cjh5414");
        Assert.assertEquals(editedTitle, afterUSer.getTodoList().get(0).getTitle());
    }

    @Test
    @Transactional
    public void testCheckTodoIsDone() throws Exception {
        Todo todo = todoRepository.findByTitle("매일 하는 일").get(0);
        utils.updatePlanedDateToToday(todo);
        mockMvc.perform(post("/todo/" + todo.getId() + "/do")
                .param("isDone", "true")
                .with(csrf())
                .with(user("cjh5414").password("1234").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }
}
