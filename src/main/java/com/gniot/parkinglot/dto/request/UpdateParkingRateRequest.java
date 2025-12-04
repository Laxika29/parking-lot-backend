package com.gniot.parkinglot.dto.request;

import com.gniot.parkinglot.constants.ParkingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class UpdateParkingRateRequest {
    private Long parkingLotId;
    private Map<ParkingType, PriceRate> priceRateMap;

    @Data
    @NoArgsConstructor
    public static class PriceRate {
        private Double bikeRate;
        private Double carRate;
        private Double heavyVehicleRate;
    }
}
