package com.example.demo.auth.service;


import com.example.demo.auth.domain.RandomCity;
import com.example.demo.auth.domain.Role;
import com.example.demo.auth.domain.User;

import java.util.List;

public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<RandomCity> findAllRandomCities();

    List<Role> findAllRoles();

    User addUser(User user);
}
