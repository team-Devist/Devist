package com.tdl.devist.controller;

import com.tdl.devist.model.Authority;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());

        return "signup";
    }

    @RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
    public String createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Authority authority = new Authority(user.getUsername(), Authority.ROLE_USER);
        user.addAuthority(authority);
        userRepository.save(user);

        return "redirect:/";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }
}
