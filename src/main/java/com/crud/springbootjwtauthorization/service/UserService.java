package com.crud.springbootjwtauthorization.service;

import com.crud.springbootjwtauthorization.model.Role;
import com.crud.springbootjwtauthorization.model.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, String username);
}
