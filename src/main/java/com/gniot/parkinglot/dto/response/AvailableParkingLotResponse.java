package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AvailableParkingLotResponse {
    private Long bikeAvailableSlots;
    private Long carAvailableSlots;
    private Long heavyVehicleAvailableSlots;
}
