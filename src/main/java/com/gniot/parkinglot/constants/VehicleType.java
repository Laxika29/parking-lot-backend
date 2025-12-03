package com.gniot.parkinglot.constants;

import com.gniot.parkinglot.exception.ParkingException;

public enum VehicleType {
    BIKE("BIKE"),
    CAR("CAR"),
    HEAVY_VEHICLE("HEAVY VEHICLE");

    private String type;

    VehicleType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static VehicleType getVehicleType(String type) {
        for (VehicleType vehicleType : VehicleType.values()) {
            if (vehicleType.getType().equals(type)) {
                return vehicleType;
            }
        }
        throw new ParkingException("Invalid vehicle type: " + type);
    }
}
