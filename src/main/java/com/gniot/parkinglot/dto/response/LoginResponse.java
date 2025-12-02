package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String name;
    private String token;
    private Long userId;
    private String role;
    private Long parkingLotId;
    private String tokenType;
    private String employeeId;
}
