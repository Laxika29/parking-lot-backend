package com.gniot.parkinglot.service;

import com.gniot.parkinglot.dto.request.CheckinParkingLotReq;
import com.gniot.parkinglot.dto.request.ParkingLotPaymentReq;
import com.gniot.parkinglot.dto.response.CheckoutVehicleResponse;
import com.gniot.parkinglot.dto.response.CommonResponse;
import com.gniot.parkinglot.dto.response.FetchParkingLotResponse;

public interface ParkingLotService {
    FetchParkingLotResponse fetchAllParkingLot();

    FetchParkingLotResponse fetchAllActiveParkingLot();

    CommonResponse checkInVehicle(CheckinParkingLotReq request);

    CheckoutVehicleResponse checkOutVehicle(CheckinParkingLotReq request);

    CommonResponse confirmPayment(ParkingLotPaymentReq request);
}
