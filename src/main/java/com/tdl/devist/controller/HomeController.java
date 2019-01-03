package com.tdl.devist.controller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping()
    public String home(final Principal principal, Model model) {
        if (principal == null)
            return "home";
        else {
            User user = userService.getUserByUserName(principal.getName());
            List<Todo> todoList = user.getTodayTodoList();
            List<Todo> unCompletedTodoList = user.getUncompletedTodayTodoList();

            model.addAttribute("todo_list", todoList);
            model.addAttribute("uncompleted_todo_list", unCompletedTodoList);

            return "user_home";
        }
    }
}