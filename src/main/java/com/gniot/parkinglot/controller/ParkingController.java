package com.gniot.parkinglot.controller;

import com.gniot.parkinglot.dto.request.CheckinParkingLotReq;
import com.gniot.parkinglot.dto.request.ParkingLotPaymentReq;
import com.gniot.parkinglot.dto.response.CheckoutVehicleResponse;
import com.gniot.parkinglot.dto.response.CommonResponse;
import com.gniot.parkinglot.dto.response.FetchParkingLotResponse;
import com.gniot.parkinglot.dto.response.FetchPendingUserApprovalResponse;
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
        return null;
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
}
