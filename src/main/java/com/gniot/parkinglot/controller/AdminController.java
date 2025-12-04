package com.gniot.parkinglot.controller;

import com.gniot.parkinglot.dto.request.CreateParkingLotRequest;
import com.gniot.parkinglot.dto.request.UpdateParkingLotRequest;
import com.gniot.parkinglot.dto.request.UpdateParkingRateRequest;
import com.gniot.parkinglot.dto.response.CommonResponse;
import com.gniot.parkinglot.dto.response.FetchPendingUserApprovalResponse;
import com.gniot.parkinglot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create/parking")
    public ResponseEntity<String> createParkingLot(@RequestBody CreateParkingLotRequest parkingLotRequest) {
        String resposeStr = adminService.createParkingLot(parkingLotRequest);
        return ResponseEntity.ok(resposeStr);
    }

    @PostMapping("/fetch/pending/users")
    public ResponseEntity<FetchPendingUserApprovalResponse> fetchPendingUserApproval() {
        FetchPendingUserApprovalResponse response = adminService.fetchAllPendingApprovalUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fetch/current/users")
    public ResponseEntity<FetchPendingUserApprovalResponse> fetchActiveUser() {
        FetchPendingUserApprovalResponse response = adminService.fetchACurrentActiveUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve/users/{empId}")
    public ResponseEntity<CommonResponse> approveUser(@PathVariable Long empId) {
        CommonResponse response = adminService.approveUser(empId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject/users/{empId}")
    public ResponseEntity<CommonResponse> rejectUser(@PathVariable Long empId) {
        CommonResponse response = adminService.rejectUser(empId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/parking/status/{parkingLotId}/{status}")
    public ResponseEntity<CommonResponse> disableParking(@PathVariable Long parkingLotId, @PathVariable String status) {
        CommonResponse response = adminService.disableParking(parkingLotId, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/parking")
    public ResponseEntity<CommonResponse> updateParkingLot(@RequestBody UpdateParkingLotRequest updateParkingLotRequest) {
        CommonResponse response = adminService.updateParkingLot(updateParkingLotRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rate/config")
    public ResponseEntity<CommonResponse> configureRate(@RequestBody UpdateParkingRateRequest request) {
        CommonResponse response = adminService.configureParkingRate(request);
        return ResponseEntity.ok(response);
    }
}
