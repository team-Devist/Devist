package com.tdl.devist.controller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.service.TodoService;
import com.tdl.devist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

        model.addAttribute("todo_list", todoList);

        return "todo_list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("todo", new Todo());

        return "addtodo";
    }

    @PostMapping("/add")
    public String add(final Principal principal, Todo todo) {
        todo.convertRepeatDayBooleanArrToByte(); // todo: 이슈 #17 참고
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
        if(userService.hasAuthorization(principal.getName(), todo )) {
            return "redirect:/denied";
        }
        todo.convertRepeatDayByteToBooleanArr();
        model.addAttribute("todo", todo);

        return "edittodo";
    }

    @PostMapping("/{id}/edit")
    public String edit(Todo todo, @PathVariable int id) {
        todo.convertRepeatDayBooleanArrToByte(); // todo: 이슈 #17 참고
        todoService.updateTodo(id, todo);

        return "redirect:/todos";
    }
}