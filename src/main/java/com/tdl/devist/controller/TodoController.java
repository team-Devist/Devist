package com.tdl.devist.controller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.service.TodoService;
import com.tdl.devist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author delf
 */
@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @RequestMapping(method = RequestMethod.GET)
    public String getTodoList(Model model) {
        String userName = getCurrentUserName();
        List<Todo> todoList = userService.getTodoListByUserName(userName);

        model.addAttribute("todolist", todoList);

        return "todolist";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(Model model) {
        model.addAttribute("todo", new Todo());

        return "addtodo";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Todo todo) {
        String userName = getCurrentUserName();
        User user = userService.getUserByUserName(userName);
        todo.setUser(user);
        todoService.addTodo(todo);

        return "redirect:/";
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
