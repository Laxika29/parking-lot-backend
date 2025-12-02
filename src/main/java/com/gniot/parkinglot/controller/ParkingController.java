package com.gniot.parkinglot.controller;

import com.gniot.parkinglot.dto.response.FetchParkingLotResponse;
import com.gniot.parkinglot.dto.response.FetchPendingUserApprovalResponse;
import com.gniot.parkinglot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
