package com.gniot.parkinglot.controller;

import com.gniot.parkinglot.dto.request.AuthRequest;
import com.gniot.parkinglot.dto.request.RegistrationRequest;
import com.gniot.parkinglot.dto.response.LoginResponse;
import com.gniot.parkinglot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest registrationRequest) {
        if (registrationRequest.getFullName() == null || registrationRequest.getFullName().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Name");
        }
        if (registrationRequest.getEmailId() == null || registrationRequest.getEmailId().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Email ID");
        }
        if (registrationRequest.getParkingLotId() == null || registrationRequest.getParkingLotId() < 1) {
            return ResponseEntity.badRequest().body("Invalid Parking Lot ID");
        }
        if (registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Password");
        }

        loginService.registerUser(registrationRequest);
        return ResponseEntity.ok("Registration Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        LoginResponse response = loginService.login(authRequest);
        return ResponseEntity.ok(response);
    }
}
