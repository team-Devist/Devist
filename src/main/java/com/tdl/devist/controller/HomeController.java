package com.tdl.devist.controller;

import com.tdl.devist.dto.TodoListDto;
import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public String home(final Principal principal, Model model) {
        if (principal == null)
            return "home";
        else {
            User user = userService.getUserByUserName(principal.getName());
            TodoListDto todoListDto = user.getTodoListDto();
            List<Todo> fixedNotDoneTodoList = todoListDto.getNotDoneTodayFixedTodoList();
            List<Todo> fixedDoneTodoList = todoListDto.getDoneTodayFixedTodoList();
            List<Todo> flexibleNotDoneTodoList = todoListDto.getNotDoneTodayFlexibleTodoList();
            List<Todo> flexibleDoneTodoList = todoListDto.getDoneTodayFlexibleTodoList();

            model.addAttribute("notdone_today_fixed_todo_list", fixedNotDoneTodoList);
            model.addAttribute("done_today_fixed_todo_list", fixedDoneTodoList);
            model.addAttribute("notdone_today_flexible_todo_list", flexibleNotDoneTodoList);
            model.addAttribute("done_today_flexible_todo_list", flexibleDoneTodoList);
            model.addAttribute("user", user);

            return "user_home";
        }
    }
}