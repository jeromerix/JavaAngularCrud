package com.crud.springbootjwtauthorization.repository;
import com.crud.springbootjwtauthorization.model.Role;
import com.crud.springbootjwtauthorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //find users by name
    Optional<User> findByUserName(String username);

    //custom query
    @Modifying
    @Query("update User set role = :role where username = :username")
        void updateUserRole(@Param("username")String username, @Param("role") Role role);

}
