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
        return "addtodo";
    }

    @PostMapping("/add")
    public String add(final Principal principal, Todo todo, final FixedRepeatDay fixedRepeatDay, final FlexibleRepeatDay flexibleRepeatDay) {
        RepeatDay repeatDay = selectRepeatDay(fixedRepeatDay, flexibleRepeatDay);
        todo.setRepeatDay(repeatDay);

        repeatDay.setTodo(todo);

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

        /* Todo: 프론트에서 분기를 나눠서 보여주는 방식을 처리 또는 아예 따로 불러오는 것을 고려 */
        if (todo.getRepeatDay() instanceof FixedRepeatDay) {
            ((FixedRepeatDay) todo.getRepeatDay()).convertRepeatDayByteToBooleanArr();
            model.addAttribute("fixedRepeatDay", todo.getRepeatDay());
            model.addAttribute("flexibleRepeatDay", new FlexibleRepeatDay());
        } else {
            model.addAttribute("fixedRepeatDay", new FixedRepeatDay());
            model.addAttribute("flexibleRepeatDay", todo.getRepeatDay());
        }

        return "edittodo";
    }

    @PostMapping("/{id}/edit")
    public String edit(Todo todo, @PathVariable int id, FixedRepeatDay fixedRepeatDay, FlexibleRepeatDay flexibleRepeatDay) {
        RepeatDay repeatDay = selectRepeatDay(fixedRepeatDay, flexibleRepeatDay);
        todo.setRepeatDay(repeatDay);

        todoService.updateTodo(id, todo);

        return "redirect:/todos";
    }

    public RepeatDay selectRepeatDay(FixedRepeatDay fixedRepeatDay, FlexibleRepeatDay flexibleRepeatDay) {
        RepeatDay repeatDay;
        if (flexibleRepeatDay.getWeeksCount() == 0) {
            fixedRepeatDay.convertRepeatDayBooleanArrToByte();
            repeatDay = fixedRepeatDay;
        } else {
            repeatDay = flexibleRepeatDay;
        }
        return repeatDay;
    }
}