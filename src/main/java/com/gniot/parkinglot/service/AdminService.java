package com.gniot.parkinglot.service;

import com.gniot.parkinglot.dto.request.CreateParkingLotRequest;
import com.gniot.parkinglot.dto.request.UpdateParkingLotRequest;
import com.gniot.parkinglot.dto.response.CommonResponse;
import com.gniot.parkinglot.dto.response.FetchPendingUserApprovalResponse;

public interface AdminService {
    String createParkingLot(CreateParkingLotRequest parkingLotRequest);

    FetchPendingUserApprovalResponse fetchAllPendingApprovalUsers();

    FetchPendingUserApprovalResponse fetchACurrentActiveUsers();

    CommonResponse approveUser(Long empId);

    CommonResponse rejectUser(Long empId);

    CommonResponse disableParking(Long parkingId, String status);

    CommonResponse updateParkingLot(UpdateParkingLotRequest updateParkingLotRequest);

}
