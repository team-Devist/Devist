package com.tdl.devist.controller;

import com.tdl.devist.model.*;
import com.tdl.devist.service.TodoService;
import com.tdl.devist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

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
            if (t.getRepeatDay() instanceof FixedRepeatDay) {
                ((FixedRepeatDay) t.getRepeatDay()).convertRepeatDayByteToBooleanArr();
            }
        }
        model.addAttribute("todo_list", todoList);

        return "todo_list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("todo", new Todo());
        model.addAttribute("fixedRepeatDay", new FixedRepeatDay());
        model.addAttribute("flexibleRepeatDay", new FlexibleRepeatDay());

        model.addAttribute("fixedOrFlexible", "fixed");
        return "addtodo";
    }

    @PostMapping("/add")
    public String add(final Principal principal, Todo todo, final FixedRepeatDay fixedRepeatDay, final FlexibleRepeatDay flexibleRepeatDay, final String fixedOrFlexible) {
        RepeatDay repeatDay = selectRepeatDay(fixedRepeatDay, flexibleRepeatDay, fixedOrFlexible);
        repeatDay.setTodo(todo);
        todo.setRepeatDay(repeatDay);
        todoService.addTodo(principal.getName(), todo);

        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id, final Principal principal) {
        logger.info("[Delete Todo] ID : {}, Todo Id : {}", principal.getName(), id);

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
            model.addAttribute("fixedRepeatDay", todo.getRepeatDay());
            model.addAttribute("flexibleRepeatDay", new FlexibleRepeatDay());
            model.addAttribute("fixedOrFlexible", "fixed");
        } else {
            model.addAttribute("fixedRepeatDay", new FixedRepeatDay());
            model.addAttribute("flexibleRepeatDay", todo.getRepeatDay());
            model.addAttribute("fixedOrFlexible", "flexible");
        }

        return "edittodo";
    }

    @PostMapping("/{id}/edit")
    public String edit(Todo todo, @PathVariable int id, FixedRepeatDay fixedRepeatDay, FlexibleRepeatDay flexibleRepeatDay, final String fixedOrFlexible) {
        RepeatDay repeatDay = selectRepeatDay(fixedRepeatDay, flexibleRepeatDay, fixedOrFlexible);
        todo.setRepeatDay(repeatDay);
        todoService.updateTodo(id, todo);

        return "redirect:/todos";
    }


    public RepeatDay selectRepeatDay(FixedRepeatDay fixedRepeatDay, FlexibleRepeatDay flexibleRepeatDay, String flag) {
        switch (flag) {
            case "fixed": return fixedRepeatDay;
            case "flexible": return flexibleRepeatDay;
        }
        throw new NoSuchElementException();
    }
}