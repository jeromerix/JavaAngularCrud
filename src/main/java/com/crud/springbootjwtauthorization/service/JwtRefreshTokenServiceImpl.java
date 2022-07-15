package com.crud.springbootjwtauthorization.service;

import com.crud.springbootjwtauthorization.model.JwtRefreshToken;
import com.crud.springbootjwtauthorization.model.User;
import com.crud.springbootjwtauthorization.repository.JwtRefreshTokenRepository;
import com.crud.springbootjwtauthorization.repository.UserRepository;
import com.crud.springbootjwtauthorization.security.UserPrincipal;
import com.crud.springbootjwtauthorization.security.jwt.JwtProvider;
import com.crud.springbootjwtauthorization.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService{

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_EXPIRATION_IN_MS;

    private JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public JwtRefreshTokenServiceImpl(JwtRefreshTokenRepository jwtRefreshTokenRepository, UserRepository userRepository, JwtProvider jwtProvider) {
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtRefreshToken createRefreshToken(Long userId){

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();

        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setUserId(userId);
        jwtRefreshToken.setCreateDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));

        return jwtRefreshTokenRepository.save(jwtRefreshToken);

    }

    @Override
    public User generateAccesTokenFromRefreshToken(String refreshTokenId){

        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshTokenId).orElseThrow();

        if(jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("JWT refresh token is not valid.");
        }

        User user = userRepository.findById(jwtRefreshToken.getUserId()).orElseThrow();

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(user.getRole().name())))
                .build();

        String accesToken = jwtProvider.generateToken(userPrincipal);

        user.setAccesToken(accesToken);
        user.setRefreshToken(refreshTokenId);

        return user;
    }
}
