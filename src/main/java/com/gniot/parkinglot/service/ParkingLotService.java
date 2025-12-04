package com.gniot.parkinglot.service;

import com.gniot.parkinglot.dto.request.CheckinParkingLotReq;
import com.gniot.parkinglot.dto.request.LockVehicleRequest;
import com.gniot.parkinglot.dto.request.ParkingLotPaymentReq;
import com.gniot.parkinglot.dto.response.*;

public interface ParkingLotService {
    FetchParkingLotResponse fetchAllParkingLot();

    FetchParkingLotResponse fetchAllActiveParkingLot();

    CommonResponse checkInVehicle(CheckinParkingLotReq request);

    CheckoutVehicleResponse checkOutVehicle(CheckinParkingLotReq request);

    CommonResponse confirmPayment(ParkingLotPaymentReq request);

    DashboardParkedVehicleResponse fetchDashboardVehicles();

    CommonResponse lockVehicle(LockVehicleRequest request);

    CommonResponse unLockVehicle(LockVehicleRequest request);

    AvailableParkingLotResponse fetchAvailableParkingLot();

    ParkingLotHistoryResponse fetchParkingLotHistory();

    ParkingLotFareDetailsResponse fetchFareDetails();

    AvailableParkingResponse fetchAvailableParkingStatus();
}
