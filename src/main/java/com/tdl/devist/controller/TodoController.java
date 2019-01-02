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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final UserService userService;
    private final TodoService todoService;

    @Autowired
    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getTodoList(Model model) {

        String userName = getCurrentUserName();
        User user = userService.getUserByUserName(userName);
        List<Todo> todoList = user.getTodoList();

        model.addAttribute("todo_list", todoList);

        return "todo_list";
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

        todoService.addTodo(user, todo);
        userService.updateUser(user);

        return "redirect:/todo";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable int id) {
        User user = userService.getUserByUserName(getCurrentUserName());
        List<Todo> todoList = user.getTodoList();

        Todo todo = todoService.findTodoById(id);
        todoList.remove(todo);

        todoService.deleteTodo(todo);

        return "redirect:/todo";
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}