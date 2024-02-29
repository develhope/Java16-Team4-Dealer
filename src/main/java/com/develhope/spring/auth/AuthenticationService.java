package com.develhope.spring.auth;


import com.develhope.spring.auth.dao.request.SignUpRequest;
import com.develhope.spring.auth.dao.request.SigninRequest;
import com.develhope.spring.auth.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
