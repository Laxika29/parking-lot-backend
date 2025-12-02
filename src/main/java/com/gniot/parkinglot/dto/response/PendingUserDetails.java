package com.gniot.parkinglot.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingUserDetails {
    private Long id;
    private String employeeName;
    private String employeeId;
    private String emailId;
    private String requestedOn;
    private String parkingLotName;
    private String parkingLotAddress;
}