package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DashboardParkedVehicleResponse {
    List<ParkedVehicleInfo> parkedVehicleInfoList;

    @Data
    @Builder
    public static class ParkedVehicleInfo {
        private Long parkingId;
        private String vehicleNumber;
        private String vehicleType;
        private String allocatedBy;
        private String exitBy;
        private String entryTime;
        private String exitTime;
        private boolean vehicleLocked;
    }
}
