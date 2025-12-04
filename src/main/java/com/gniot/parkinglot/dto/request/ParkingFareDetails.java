package com.gniot.parkinglot.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ParkingFareDetails {
    private String vehicleType;
    private String hourlyRate;
    private String dailyRate;
    private String weeklyRate;
    private String monthlyRate;

    public ParkingFareDetails(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}