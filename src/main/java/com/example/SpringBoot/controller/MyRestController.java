package com.example.SpringBoot.controller;

import com.example.SpringBoot.module.User;
import com.example.SpringBoot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/adding/addUser")
    public String saveNewUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/allUsers";
    }
}
