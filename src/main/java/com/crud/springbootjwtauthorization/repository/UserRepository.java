package com.crud.springbootjwtauthorization.repository;

import com.crud.springbootjwtauthorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

}
