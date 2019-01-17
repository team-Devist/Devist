package com.tdl.devist.apicontroller;

import com.tdl.devist.apicontroller.json_pojo.UserHomeData;
import com.tdl.devist.model.User;
import com.tdl.devist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class TodoAPIController {
    private static final Logger logger = LoggerFactory.getLogger(TodoAPIController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user-home-data")
    public UserHomeData getTodoList(final Principal principal) {
        if (principal == null) {
            logger.info("비로그인 사용자 접근");
        }
        else {
            User user = userService.getUserByUserName(principal.getName());
            int todoSize = user.getTodayTodoList().size();
            int completedTodoSize = user.getCompletedTodayTodoList().size();
            double userDoneRate = user.getDoneRate();

            return new UserHomeData(todoSize, completedTodoSize, userDoneRate);
        }
        return null;
    }
}
