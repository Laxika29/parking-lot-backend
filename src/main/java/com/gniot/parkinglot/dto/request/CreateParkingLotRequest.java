package com.gniot.parkinglot.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateParkingLotRequest {
    private String parkingLotName;
    private String address;
    private Long bikeCapacity;
    private Long carCapacity;
    private Long heavyVehicleCapacity;
    private Double longitude;
    private Double latitude;
}
