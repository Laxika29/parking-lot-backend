package com.gniot.parkinglot.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FetchParkingLotResponse {
    List<ParkingLotData> parkingLots;
}
