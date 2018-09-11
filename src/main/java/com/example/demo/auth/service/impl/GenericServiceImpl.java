package com.example.demo.auth.service.impl;


import com.example.demo.auth.domain.RandomCity;
import com.example.demo.auth.domain.Role;
import com.example.demo.auth.domain.User;
import com.example.demo.auth.repository.RandomCityRepository;
import com.example.demo.auth.repository.RoleRepository;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.auth.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RandomCityRepository randomCityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public List<RandomCity> findAllRandomCities() {
        return (List<RandomCity>)randomCityRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles(){
        return (List<Role> )roleRepository.findAll();
    }

    @Override
    public User addUser(User data){

        return userRepository.save(data);
    }
}
