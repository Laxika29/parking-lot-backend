package com.gniot.parkinglot.constants;

import com.gniot.parkinglot.exception.ParkingException;

public enum ParkingType {
    HOURLY("HOURLY"),
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY");

    private String type;

    ParkingType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ParkingType getParkingType(String type) {
        for (ParkingType parkingType : ParkingType.values()) {
            if (parkingType.getType().equals(type)) {
                return parkingType;
            }
        }
        throw new ParkingException("Invalid parking type: " + type);
    }
}
