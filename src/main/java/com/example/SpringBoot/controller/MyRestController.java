package com.example.SpringBoot.controller;

import com.example.SpringBoot.module.User;
import com.example.SpringBoot.service.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAll() {
        System.out.println(userService.findAll());
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/page/user")
    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/adding/addUser")
    public ResponseEntity<List<User>> post(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<User>> put(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<User>> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(userService.findAll());
    }
}

/*@RestController
@RequestMapping("/admin/api")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(userService.findByLogin("admin"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<User>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/")
    public ResponseEntity<List<User>> post(@RequestBody User user) {
        userService.create(user);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<User>> put(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.ok(userService.getAllUsers());
    }
}*/
