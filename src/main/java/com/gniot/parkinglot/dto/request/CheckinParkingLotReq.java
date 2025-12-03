package com.gniot.parkinglot.dto.request;

import lombok.Data;

@Data
public class CheckinParkingLotReq {
    private Long parkingId;
    private String vehicleType;
    private String vehicleNumber;
    private String parkingType;
}
