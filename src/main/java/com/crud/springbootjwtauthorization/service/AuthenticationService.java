package com.crud.springbootjwtauthorization.service;

import com.crud.springbootjwtauthorization.model.User;

public interface AuthenticationService {

    User signInAndReturnJWT(User signInRequest);
}
