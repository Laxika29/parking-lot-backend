package com.gniot.parkinglot.service;

import com.gniot.parkinglot.dto.request.AuthRequest;
import com.gniot.parkinglot.dto.request.RegistrationRequest;
import com.gniot.parkinglot.dto.response.LoginResponse;

public interface LoginService {
    void registerUser(RegistrationRequest registrationRequest);

    LoginResponse login(AuthRequest authRequest);
}
