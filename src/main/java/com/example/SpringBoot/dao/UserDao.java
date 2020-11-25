package com.example.SpringBoot.dao;

import com.example.SpringBoot.module.Role;
import com.example.SpringBoot.module.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    User getById(long id);
    void save(User user);
    void remove(long id);
    void update(long id, User user);
    User getUserByUserName(String name);
}
