package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CheckoutVehicleResponse {
    private Long parkingId;
    private String vehicleNumber;
    private Double parkingCharge;
    private String entryTime;
    private String exitTime;

}
