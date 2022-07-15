package com.crud.springbootjwtauthorization.service;

import com.crud.springbootjwtauthorization.model.JwtRefreshToken;
import com.crud.springbootjwtauthorization.model.User;

public interface JwtRefreshTokenService {
    JwtRefreshToken createRefreshToken(Long userId);

    User generateAccesTokenFromRefreshToken(String refreshTokenId);
}
