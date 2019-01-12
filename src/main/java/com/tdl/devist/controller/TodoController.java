package com.tdl.devist.controller;

import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.service.TodoService;
import com.tdl.devist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public String getTodoList(Principal principal, Model model) {
        User user = userService.getUserByUserName(principal.getName());
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
    public String add(final Principal principal, Todo todo) {
        User user = userService.getUserByUserName(principal.getName());
        todo.convertRepeatDayBooleanArrToByte(); // todo: 이슈 #17 참고
        todoService.addTodo(user, todo);

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable int id, final Principal principal) {
        User user = userService.getUserByUserName(principal.getName());
        todoService.deleteTodo(user, id);

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editForm(Model model, @PathVariable int id, final Principal principal) {
        Todo todo = todoService.findTodoById(id);
        if(userService.hasAuthorization(principal.getName(), todo )) {
            return "redirect:/denied";
        }
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
    public @ResponseBody String doTodo(@PathVariable int id, @RequestParam boolean isDone, final Principal principal) {
        todoService.setTodoIsDone(id, isDone);
        todoService.updateDoneRate(id);
        userService.updateDoneRate(principal.getName());
        return "ok";
    }
}