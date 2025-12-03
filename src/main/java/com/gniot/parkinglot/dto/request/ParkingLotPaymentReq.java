package com.gniot.parkinglot.dto.request;

import lombok.Data;

@Data
public class ParkingLotPaymentReq {
    private Long id;
    private String paymentMode;
    private String transactionId;
    private Double paymentAmount;
    private String paymentFor;
}
