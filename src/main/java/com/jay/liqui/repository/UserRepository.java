package com.jay.liqui.repository;

import com.jay.liqui.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository <User, String>{
    //findBy + FieldName
    Optional<User> findByUsername(String username);

    void deleteById(String userId);
}
