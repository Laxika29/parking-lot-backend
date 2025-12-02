package com.gniot.parkinglot.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
    private boolean isOtpValidation;
    private String otpCode;
}
