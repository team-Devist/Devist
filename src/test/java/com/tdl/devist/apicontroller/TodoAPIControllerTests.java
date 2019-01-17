package com.tdl.devist.apicontroller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
@WebAppConfiguration
public class TodoAPIControllerTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    public void testGetUserHomeData() throws Exception {
        User user = userRepository.getOne("cjh5414");
        int todoSize = user.getTodayTodoList().size();
        int completedTodoSize = user.getCompletedTodayTodoList().size();
        double userDoneRate = user.getDoneRate();

        mockMvc.perform(get("/api/user-home-data")
                .with(user("cjh5414").password("1234").roles("USER"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.todoSize", is(todoSize)))
                .andExpect(jsonPath("$.completedTodoSize", is(completedTodoSize)))
                .andExpect(jsonPath("$.userDoneRate", is(userDoneRate)));
    }

    @Test
    @Transactional
    public void testCheckTodoIsDone() throws Exception {
        Todo todo = todoRepository.findByTitle("매일 하는 일").get(0);
        mockMvc.perform(post("/todo/" + todo.getId() + "/do")
                .param("isDone", "true")
                .with(csrf())
                .with(user("cjh5414").password("1234").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }
}
