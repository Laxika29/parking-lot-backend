package com.gniot.parkinglot.dto.request;

import lombok.Data;

@Data
public class LockVehicleRequest {
    private Long parkingId;
    private String lockReason;
    private String paymentMode;
    private Double paymentAmount;
}
