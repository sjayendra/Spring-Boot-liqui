package com.jay.liqui.service;


import com.jay.liqui.model.User;
import com.jay.liqui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    //It will be provided on WebSecurityConfig as @Bean
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(final User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //save = create or update
    @Override
    public User updateUser(final User user){
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final String userId){
        userRepository.deleteById(userId);
    }

    @Override
    public User findByUsername(final String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Long numberOfUsers(){
        return userRepository.count();
    }

}
