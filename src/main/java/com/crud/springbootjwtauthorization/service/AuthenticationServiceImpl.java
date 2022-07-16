package com.crud.springbootjwtauthorization.service;

import com.crud.springbootjwtauthorization.model.User;
import com.crud.springbootjwtauthorization.security.UserPrincipal;
import com.crud.springbootjwtauthorization.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private JwtRefreshTokenService jwtRefreshTokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider, JwtRefreshTokenService jwtRefreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @Override
    public User signInAndReturnJWT(User signInRequest){

        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        User signInUser = userPrincipal.getUser();
        signInUser.setAccesToken(jwt);
        signInUser.setRefreshToken(jwtRefreshTokenService.createRefreshToken(signInUser.getId()).getTokenId());

        return signInUser;
    }
}
