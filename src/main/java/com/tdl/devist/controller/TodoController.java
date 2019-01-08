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
import org.springframework.web.bind.annotation.*;

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
        todo.convertRepeatDayBooleanArrToByte(); // todo: 이슈 #17 참고
        todoService.addTodo(user, todo);
        // userService.updateUser(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable int id) {
        User user = userService.getUserByUserName(getCurrentUserName());
        todoService.deleteTodo(user, id);

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editForm(Model model, @PathVariable int id) {
        Todo todo = todoService.findTodoById(id);
        todo.convertRepeatDayByteToBooleanArr();
        model.addAttribute("todo", todo);

        return "edittodo";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String edit(Todo todo, @PathVariable int id) {
        todo.convertRepeatDayBooleanArrToByte(); // todo: 이슈 #17 참고
        todoService.updateTodo(id, todo);

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/do", method = RequestMethod.POST)
    public @ResponseBody
    String doTodo(@PathVariable int id, @RequestParam boolean isDone) {
        todoService.setTodoIsDone(id, isDone);
        todoService.updateDoneRate(id);
        return "ok";
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}