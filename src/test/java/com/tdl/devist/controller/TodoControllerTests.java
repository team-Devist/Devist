package com.tdl.devist.controller;

import com.tdl.devist.model.User;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
@ContextConfiguration
@WebAppConfiguration
public class TodoControllerTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

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
    public void testAddTodo() throws Exception {
        // 테스트 작성중. 실패 정상
        mockMvc.perform(post("/todo/add")
                .param("title", "test title")
                .param("description", "test description")
                .param("repeatDay", "1")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckTodoIsDone() throws Exception {
        mockMvc.perform(post("/todo/0/do")
                .param("isDone", "true")
                .with(csrf())
                .with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }
}
