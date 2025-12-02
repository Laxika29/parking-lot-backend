package com.gniot.parkinglot.service;

import com.gniot.parkinglot.dto.response.FetchParkingLotResponse;

public interface ParkingLotService {
    FetchParkingLotResponse fetchAllParkingLot();

    FetchParkingLotResponse fetchAllActiveParkingLot();
}
