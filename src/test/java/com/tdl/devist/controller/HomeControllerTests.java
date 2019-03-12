package com.tdl.devist.controller;


import com.tdl.devist.service.TodoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class HomeControllerTests {
    private static final int NAME = 0;
    private static final int PWD = 1;
    private static final String[] USER_A = {"cjh5414", "1234"};
    private static final String[] USER_B = {"nesoy", "1234"};

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
    @Transactional
    public void 비사용자일때_홈_페이지를_정상적으로_가져온다() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @Transactional
    public void 사용자_홈_페이지를_정상적으로_가져온다() throws Exception {
        mockMvc.perform(get("/")
                .with(user(USER_A[NAME]).password(USER_A[PWD]).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("user_home")).andReturn();
    }

    @Test
    @Transactional
    public void 오늘_할일을_정상적으로_가져온다() throws Exception {
        MvcResult result = mockMvc.perform(get("/")
                .with(user(USER_A[NAME]).password(USER_A[PWD]).roles("USER"))).andReturn();

        // NOTE: 이하의 검증은 import.sql에 종속된다

        Assert.assertTrue(result.getResponse().getContentAsString().contains("매일 하는 일"));
    }

    @Test
    @Transactional
    public void 오늘_완료된_일을_정상적으로_가져온다() throws Exception {

        // NOTE: 이하의 검증은 import.sql에 종속된다

        mockMvc.perform(post("/api/todos/1/do")
                .with(user(USER_A[NAME]).password(USER_A[PWD]).roles("USER"))
                .with(csrf())
                .param("isDone", "true"))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/")
                .with(user(USER_A[NAME]).password(USER_A[PWD]).roles("USER"))).andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().contains("매일 하는 일"));
    }

    @Test
    @Transactional
    public void testHome() throws Exception {
        mockMvc.perform(get("/")
                .with(user(USER_B[NAME]).password(USER_B[PWD]).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("user_home"))
                .andExpect(content().string(is(not(containsString("매일 하는 일")))))
                .andExpect(content().string(is(not(containsString("매일 하는 일인데 테스트 할라고 완료 시킴")))));

        mockMvc.perform(get("/")
                .with(user(USER_A[NAME]).password(USER_B[PWD]).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("user_home"))
                .andExpect(content().string(containsString("매일 하는 일")))
                .andExpect(content().string(containsString("매일 하는 일인데 테스트 할라고 완료 시킴")));
    }

    private int getSizeOfModel(ModelAndView mav, String modelName) {
        List list = (List) Objects.requireNonNull(mav).getModel().get(modelName);
        return Objects.requireNonNull(list).size();
    }
}

