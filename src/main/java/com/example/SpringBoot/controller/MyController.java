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

    @GetMapping(value = "/allUsers")
    public String showAllUsers(Model model) {
        model.addAttribute("usersAll", userService.findAll());
        return "allUsers";
    }

    @GetMapping(value = "/{id}")
    public String showOneUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("userByid", userService.findById(id));
        return "getUserById";
    }

    @GetMapping(value = "/addUser")
    public String giveNewUser(Model model) {
        model.addAttribute("newUser", new User());
        return "newuser";
    }

    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/allUsers";
    }

    @GetMapping("/{id}/admin")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:/allUsers";
    }

    @DeleteMapping("/{id}/admin")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/allUsers";
    }

    @GetMapping("page/user")
    public String showOverview(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("userGotIn", user);
        return "user";
    }

}
