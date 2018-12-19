package com.tdl.controller;

import com.tdl.model.User;
import com.tdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author delf
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
}
