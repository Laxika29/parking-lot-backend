package com.gniot.parkinglot.service;

import com.gniot.parkinglot.dto.request.RegistrationRequest;

public interface LoginService {
    void registerUser(RegistrationRequest registrationRequest);
}
