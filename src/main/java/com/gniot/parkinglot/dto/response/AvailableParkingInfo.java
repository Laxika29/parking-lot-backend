package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableParkingInfo {
    private String vehicleType;
    private String parkingLotName;
    private String address;
    private Long totalSpace;
    private Long occupiedSpace;
    private Long availableSpace;
    private Double distanceInKm;
}
