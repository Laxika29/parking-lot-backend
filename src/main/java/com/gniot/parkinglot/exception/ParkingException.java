package com.gniot.parkinglot.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingException extends RuntimeException {
    private String message;
    private String code;
    private String status;

    public ParkingException(String code, String status, String message) {
        super(message);
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public ParkingException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
        this.status = "FAILED";
    }
}
