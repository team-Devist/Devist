package com.tdl.devist.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class HomeControllerTests {
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
    public void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));

        mockMvc.perform(get("/")
                .with(user("nesoy").password("1234").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("user_home"))
                .andExpect(content().string(is(not(containsString("알고리즘 문제 풀기")))))
                .andExpect(content().string(is(not(containsString("spring 동영상 강의 보기")))));

        mockMvc.perform(get("/")
                .with(user("cjh5414").password("1234").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("user_home"))
                .andExpect(content().string(containsString("알고리즘 문제 풀기")))
                .andExpect(content().string(containsString("spring 동영상 강의 보기")));
    }
}

