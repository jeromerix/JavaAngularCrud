package com.crud.springbootjwtauthorization.service;

import com.crud.springbootjwtauthorization.model.Role;
import com.crud.springbootjwtauthorization.model.User;
import com.crud.springbootjwtauthorization.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //encrypt user password
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    //save user with standard user role and timestamp
    public User saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());

        return userRepository.save(user);
    }
    //find user by name
    @Override
    public Optional<User>findByUsername(String username){
        return userRepository.findByUserName(username);
    }

    //change user roles
    @Override
    public void changeRole(Role newrole, String username)
    {
        userRepository.updateUserRole(username, newrole);
    }
}
