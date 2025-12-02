package com.gniot.parkinglot.dto.request;

import lombok.Data;

@Data
public class UpdateParkingLotRequest {
    private Long id;
    private String parkingLotName;
    private String address;
    private Long bikeCapacity;
    private Long carCapacity;
    private Long heavyVehicleCapacity;
}
