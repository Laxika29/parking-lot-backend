package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionUserInfo {
    private Long subscriptionId;
    private String vehicleNumber;
    private String subscriptionType;
    private String subscriptionFrequency;
    private String purchasedOn;
    private String validTill;
    private Double amountPaid;
    private String status;
}
