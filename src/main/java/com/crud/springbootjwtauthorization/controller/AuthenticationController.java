package com.crud.springbootjwtauthorization.controller;

import com.crud.springbootjwtauthorization.model.User;
import com.crud.springbootjwtauthorization.service.AuthenticationService;
import com.crud.springbootjwtauthorization.service.JwtRefreshTokenService;
import com.crud.springbootjwtauthorization.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private UserService userService;
    private JwtRefreshTokenService jwtRefreshTokenService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService, JwtRefreshTokenService jwtRefreshTokenService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @PostMapping("sign-up")
    public ResponseEntity<?>signUp(@RequestBody User user){

        if(userService.findByUsername(user.getUsername()).isPresent()){

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user){

        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String token){

        return ResponseEntity.ok(jwtRefreshTokenService.generateAccesTokenFromRefreshToken(token));

    }


}
