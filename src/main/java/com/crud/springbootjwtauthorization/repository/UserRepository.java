package com.crud.springbootjwtauthorization.repository;

import com.crud.springbootjwtauthorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.management.relation.Role;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //find users by name
    Optional<User> findByUserName(String username);

    @Modifying

    //custom query
    @Query("update User set role = :role where username = :username")
        void updateUserRole(@Param("username")String username, @Param("role") Role role);

}
