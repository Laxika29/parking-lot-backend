package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ParkingLotHistoryResponse {
    private List<ParkingLotHistory> parkingLotHistories;

    @Data
    @Builder
    public static class ParkingLotHistory {
        private String vehicleNo;
        private String type;
        private String entryTime;
        private String exitTime;
        private String paymentMode;
        private String paymentType;
        private Double charge;
    }
}
