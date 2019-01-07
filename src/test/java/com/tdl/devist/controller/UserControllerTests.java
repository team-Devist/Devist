package com.tdl.devist.controller;


import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
@ContextConfiguration
@WebAppConfiguration
public class UserControllerTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @MockBean
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
        mockMvc.perform(post("/signup")
                .param("username", "user1")
                .param("password", "user1234")
                .param("name", "name1")
                .with(csrf()))
                .andExpect(status().isOk());

        Assert.assertEquals(5, userRepository.findAll().size());
        // Todo: test 통과 시키기. /signup 성공 이후 생성한 User를 userRepository를 통해서 가져올 수 없음.

        User user = userRepository.getOne("user1");
        Assert.assertTrue(user.isEnabled());
        Assert.assertTrue(user.isEnabled());
        Assert.assertEquals(100.00, user.getDoneRate(), 00.01);
        Assert.assertEquals("name1", user.getName());
    }
}
