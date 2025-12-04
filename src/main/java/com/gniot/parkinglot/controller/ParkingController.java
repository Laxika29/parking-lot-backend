package com.gniot.parkinglot.controller;

import com.gniot.parkinglot.dto.request.ActivateSubscriptionRequest;
import com.gniot.parkinglot.dto.request.CheckinParkingLotReq;
import com.gniot.parkinglot.dto.request.LockVehicleRequest;
import com.gniot.parkinglot.dto.request.ParkingLotPaymentReq;
import com.gniot.parkinglot.dto.response.*;
import com.gniot.parkinglot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ParkingController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("/fetch/parking/lot")
    public ResponseEntity<FetchParkingLotResponse> fetchAllParkingLot() {
        FetchParkingLotResponse response = parkingLotService.fetchAllParkingLot();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/active/parking/lot")
    public ResponseEntity<FetchParkingLotResponse> fetchAllActiveParkingLot() {
        FetchParkingLotResponse response = parkingLotService.fetchAllActiveParkingLot();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-in")
    public ResponseEntity<CommonResponse> checkinVehicle(@RequestBody CheckinParkingLotReq request) {
        CommonResponse commonResponse = parkingLotService.checkInVehicle(request);
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/check-out")
    public ResponseEntity<CheckoutVehicleResponse> checkOutVehicle(@RequestBody CheckinParkingLotReq request) {
        CheckoutVehicleResponse response = parkingLotService.checkOutVehicle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-checkout")
    public ResponseEntity<CommonResponse> checkOutVehicle(@RequestBody ParkingLotPaymentReq request) {
        CommonResponse response = parkingLotService.confirmPayment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/dashboard/vehicles")
    public ResponseEntity<DashboardParkedVehicleResponse> fetchDashboardVehicles() {
        DashboardParkedVehicleResponse response = parkingLotService.fetchDashboardVehicles();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/lock/vehicle")
    public ResponseEntity<CommonResponse> lockVehicle(@RequestBody LockVehicleRequest request) {
        CommonResponse response = parkingLotService.lockVehicle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unlock/vehicle")
    public ResponseEntity<CommonResponse> unLockVehicle(@RequestBody LockVehicleRequest request) {
        CommonResponse response = parkingLotService.unLockVehicle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/available/parking/lot")
    public ResponseEntity<AvailableParkingLotResponse> fetchAvailableParkingLot() {
        AvailableParkingLotResponse response = parkingLotService.fetchAvailableParkingLot();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/parking/history")
    public ResponseEntity<ParkingLotHistoryResponse> fetchParkingLotHistory() {
        ParkingLotHistoryResponse response = parkingLotService.fetchParkingLotHistory();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/fare/Details")
    public ResponseEntity<ParkingLotFareDetailsResponse> fetchFareDetails() {
        ParkingLotFareDetailsResponse response = parkingLotService.fetchFareDetails();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/parking/availability")
    public ResponseEntity<AvailableParkingResponse> fetchAvailableParkingStatus() {
        AvailableParkingResponse response = parkingLotService.fetchAvailableParkingStatus();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate/subscription")
    public ResponseEntity<CommonResponse> activateSubscription(@RequestBody ActivateSubscriptionRequest request) {
        CommonResponse response = parkingLotService.activateSubscription(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/subscription")
    public ResponseEntity<SubscriptionUserResponse> fetchSubscriptionUserDetails() {
        SubscriptionUserResponse response = parkingLotService.fetchSubscriptionUserDetails();
        return ResponseEntity.ok(response);
    }
}
