package com.tdl.devist.controller;


import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
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

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class UserControllerTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    public void testCreateUser() throws Exception {

        int size = userRepository.findAll().size();

        mockMvc.perform(post("/signup")
                .param("username", "user1")
                .param("password", "user1234")
                .param("name", "name1")
                .with(csrf()))
                // .andDo(print()) for debug
                .andExpect(status().is3xxRedirection());

        Assert.assertEquals(size + 1, userRepository.findAll().size());

        User user = userRepository.getOne("user1");
        Assert.assertTrue(user.isEnabled());
        Assert.assertTrue(user.isEnabled());
        Assert.assertEquals(100.00, user.getDoneRate(), 00.01);
        Assert.assertEquals("name1", user.getName());
    }
}
