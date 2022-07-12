package com.crud.springbootjwtauthorization.security.jwt;

import com.crud.springbootjwtauthorization.security.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {
    String generateToken(UserPrincipal auth);

    UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
