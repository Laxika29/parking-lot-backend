package com.gniot.parkinglot.constants;

public enum Status {
    ACTIVE("ACTIVE", 0),
    INACTIVE("INACTIVE", 1),
    PENDING_FOR_APPROVAL("PENDING_FOR_APPROVAL", 3);

    private final String status;
    private final int value;

    Status(String status, int value) {
        this.status = status;
        this.value = value;
    }


}
