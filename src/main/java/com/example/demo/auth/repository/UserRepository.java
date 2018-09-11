package com.example.demo.auth.repository;


import com.example.demo.auth.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by nydiarra on 06/05/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);


    @Transactional
    @Query(value = "SELECT *  FROM app_user ", nativeQuery = true)
    Optional<User> get();

}
