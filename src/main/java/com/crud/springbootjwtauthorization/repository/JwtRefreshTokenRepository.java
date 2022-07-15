package com.crud.springbootjwtauthorization.repository;

import com.crud.springbootjwtauthorization.model.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {
}
