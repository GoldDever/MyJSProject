package com.example.SpringBoot.controller;

import com.example.SpringBoot.module.User;
import com.example.SpringBoot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class MyController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/")
    public String firstPage(Model model) {
        return "firstView";
    }

    @GetMapping("/{id}/admin")
    public String editUser(@PathVariable("id") long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("userGotIn", user);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.findById(id));
        return "newOne";
    }

    @GetMapping("page/user")
    public String showOverview(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("userGotIn", user);
        return "trening";
    }

/*
    @GetMapping(value = "/allUsers")
    public String showAllUsers(Model model) {
        model.addAttribute("usersAll", userService.findAll());
        return "allUsers";
    }

    @GetMapping(value = "showOne/{id}")
    public String showOneUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("userByid", userService.findById(id));
        return "getUserById";
    }

    @GetMapping("/adding/addUser")
    public String giveNewUser(Model model) {
        model.addAttribute("newUser", new User());
        return "/newuser";
    }

    @PostMapping("/adding/addUser")
    public String saveNewUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/allUsers";
    }

    @GetMapping("/{id}/admin")
    public String editUser(@PathVariable("id") long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("userGotIn", user);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.findById(id));
        return "tren";
    }

    @PatchMapping("spot/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        System.out.println("Inside patchmapping");
        return "redirect:/{id}/admin";
    }

    @DeleteMapping("/{id}/admin")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/{id}/admin";
    }

    @GetMapping("page/user")
    public String showOverview(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("userGotIn", user);
        return "trening";
    }

    @GetMapping("/us")
    public String trening() {
        return "user";
    }*/

}
