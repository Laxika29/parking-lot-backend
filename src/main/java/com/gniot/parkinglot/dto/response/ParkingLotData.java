package com.gniot.parkinglot.dto.response;

import lombok.Data;

@Data
public class ParkingLotData {
    private Long id;

    private String parkingLotName;

    private String address;

    private Long bikeCapacity;

    private Long carCapacity;

    private Long heavyVehicleCapacity;

    private String status;

    public ParkingLotData(Long id, String parkingLotName, String address, Long bikeCapacity, Long carCapacity, Long heavyVehicleCapacity, String status) {
        this.id = id;
        this.parkingLotName = parkingLotName;
        this.address = address;
        this.bikeCapacity = bikeCapacity;
        this.carCapacity = carCapacity;
        this.heavyVehicleCapacity = heavyVehicleCapacity;
        this.status = status;
    }
}
