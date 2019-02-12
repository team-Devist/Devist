package com.tdl.devist.controller;

import com.tdl.devist.model.*;
import com.tdl.devist.service.TodoService;
import com.tdl.devist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ObjectUtils;

import java.lang.annotation.Repeatable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final static Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final UserService userService;
    private final TodoService todoService;

    @Autowired
    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @GetMapping
    public String getTodoList(Principal principal, Model model) {
        User user = userService.getUserByUserName(principal.getName());
        List<Todo> todoList = user.getTodoList();
        for (Todo t : todoList) {
            ((FixedRepeatDay) t.getRepeatDay()).convertRepeatDayByteToBooleanArr();
        }
        model.addAttribute("todo_list", todoList);

        return "todo_list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("todo", new Todo());
        model.addAttribute("fixedRepeatDay", new FixedRepeatDay());
        model.addAttribute("FlexibleRepeatDay", new FlexibleRepeatDay());
        return "addtodo";
    }

    @PostMapping("/add")
    public String add(final Principal principal, Todo todo, final FixedRepeatDay fixedRepeatDay, final FlexibleRepeatDay flexibleRepeatDay) {
        fixedRepeatDay.convertRepeatDayBooleanArrToByte();
        todo.setRepeatDay(fixedRepeatDay);
        fixedRepeatDay.setTodo(todo);

        todoService.addTodo(principal.getName(), todo);

        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id, final Principal principal) {
        User user = userService.getUserByUserName(principal.getName());
        todoService.deleteTodo(user, id);

        return "redirect:/todos";
    }

    @GetMapping("/{id}/edit")
    public String editForm(Model model, @PathVariable int id, final Principal principal) {
        Todo todo = todoService.findTodoById(id);
        if (userService.hasAuthorization(principal.getName(), todo)) {
            return "redirect:/denied";
        }

        model.addAttribute("todo", todo);

        if (todo.getRepeatDay() instanceof FixedRepeatDay) {
            ((FixedRepeatDay) todo.getRepeatDay()).convertRepeatDayByteToBooleanArr();
            model.addAttribute("fixedRepeatDay", (FixedRepeatDay) todo.getRepeatDay());
        } else {
            model.addAttribute("FlexibleRepeatDay", (FlexibleRepeatDay) todo.getRepeatDay());
        }

        return "edittodo";
    }

    @PostMapping("/{id}/edit")
    public String edit(Todo todo, @PathVariable int id, FixedRepeatDay fixedRepeatDay, FlexibleRepeatDay flexibleRepeatDay) {
        fixedRepeatDay.convertRepeatDayBooleanArrToByte();
        todo.setRepeatDay(fixedRepeatDay);

        todoService.updateTodo(id, todo);

        return "redirect:/todos";
    }
}