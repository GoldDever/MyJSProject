package com.example.SpringBoot.dao;


import com.example.SpringBoot.module.Role;
import com.example.SpringBoot.module.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao{


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        return entityManager.createQuery
                ("select u from User u", User.class).getResultList();
    }

    @Override
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(long id, User updatedUser) {
       /* User user = getById(id);
        user.setAge(updatedUser.getAge());
        user.setName(updatedUser.getName());
        user.setLastName(updatedUser.getLastName());*/
        entityManager.merge(updatedUser);
    }

    @Override
    public void remove(long id) {
        User user = getById(id);
        entityManager.remove(user);
    }

    @Override
    public User getUserByUserName(String login) {
        Query q = (Query) entityManager.createQuery("SELECT u FROM User u WHERE u.login=:name");
        q.setParameter("name", login);
        User user = (User) q.getSingleResult();
        return user;
    }

    @Override
    public Role findRoleByUserName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT u FROM Role u WHERE u.role=:name", Role.class);
        query.setParameter("name", name);
        Role role = query.getSingleResult();
        return role;
    }
}
