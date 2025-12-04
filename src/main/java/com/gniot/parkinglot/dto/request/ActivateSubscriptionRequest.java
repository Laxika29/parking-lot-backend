package com.gniot.parkinglot.dto.request;

import lombok.Data;

@Data
public class ActivateSubscriptionRequest {
    private String vehicleNumber;
    private String vehicleType;
    private String subscriptionType;
    private String subscriptionFrequency;
}
