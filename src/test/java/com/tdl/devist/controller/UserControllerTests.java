package com.tdl.devist.controller;


import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class UserControllerTests {
    private MockMvc mockMvc;

    @MockBean
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/signup")
                .param("username", "user1")
                .param("password", "user1234")
                .param("name", "name1"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/"));

        User user = userRepository.getOne("user1");
        Assert.assertEquals("name1", user.getName());
    }

}
