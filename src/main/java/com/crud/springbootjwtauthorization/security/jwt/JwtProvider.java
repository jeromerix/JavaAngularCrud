package com.crud.springbootjwtauthorization.security.jwt;

import com.crud.springbootjwtauthorization.security.UserPrincipal;

public interface JwtProvider {
    String generateToken(UserPrincipal auth);
}
