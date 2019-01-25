package com.tdl.devist.controller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.junit.Assert;
import com.tdl.devist.repository.TodoRepository;
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

import javax.transaction.Transactional;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class TodoControllerTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

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
    public void testGetTodoListPage() throws Exception {
        mockMvc.perform(get("/todos")
                .with(user("admin").password("1234").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTodoAddForm() throws Exception {
        mockMvc.perform(get("/todos/add")
                .with(user("admin").password("1234").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testAddTodo() throws Exception {
        int todoSize = todoRepository.findAll().size();

        mockMvc.perform(post("/todos/add")
                .with(user("admin").password("1234").roles("USER", "ADMIN"))
                .param("title", "test title")
                .param("description", "test description")
                .param("repeatDay", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Assert.assertEquals(todoSize + 1, todoRepository.findAll().size());
        Todo todo = todoRepository.findByTitle("test title").get(0);
        Assert.assertEquals("test description", todo.getDescription());
    }

    @Test
    @Transactional
    public void testDeleteTodo() throws Exception {
        User user = userRepository.getOne("cjh5414");
        List<Todo> todoList = user.getTodoList();
        int size = todoList.size();
        Assert.assertNotEquals(0, size);
        int todoId = todoList.get(0).getId();

        mockMvc.perform(post("/todos/" + todoId + "/delete")
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
        mockMvc.perform(post("/todos/" + todoId + "/edit")
                .with(user("cjh5414").password("1234").roles("USER"))
                .param("title", editedTitle)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        User afterUSer = userRepository.getOne("cjh5414");
        Assert.assertEquals(editedTitle, afterUSer.getTodoList().get(0).getTitle());
    }

    @Test
    @Transactional
    public void 권한_없는_사용자가_수정페이지를_요청하면_404페이지를_반환한다() throws Exception {
        final String username = "cjh5414";
        User user = userRepository.getOne(username);
        List<Todo> todoList = user.getTodoList();
        int todoId = todoList.get(0).getId();

        MvcResult result = mockMvc.perform(get("/todos/" + todoId + "/edit")
                .with(user("nesoy").password("1234").roles("USER"))
                .with(csrf()))
                .andExpect(status().is3xxRedirection()).andReturn();

        Assert.assertEquals("/denied", result.getResponse().getRedirectedUrl());
    }

    @Test
    @Transactional
    public void 권한_있는_사용자가_수정페이지를_요청하면_정상_반환한다() throws Exception {
        final String username = "cjh5414";
        User user = userRepository.getOne(username);
        List<Todo> todoList = user.getTodoList();
        int todoId = todoList.get(0).getId();

        mockMvc.perform(get("/todos/" + todoId + "/edit")
                .with(user(username).password("1234").roles("USER"))
                .with(csrf()))
                .andExpect(status().isOk());
    }
}
