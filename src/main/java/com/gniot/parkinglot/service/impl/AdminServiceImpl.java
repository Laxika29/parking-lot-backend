package com.gniot.parkinglot.service.impl;

import com.gniot.parkinglot.constants.EmailHtmlConstants;
import com.gniot.parkinglot.constants.Status;
import com.gniot.parkinglot.dao.ParkingLotMasterRepository;
import com.gniot.parkinglot.dao.UserDetailsRepository;
import com.gniot.parkinglot.dto.request.CreateParkingLotRequest;
import com.gniot.parkinglot.dto.request.UpdateParkingLotRequest;
import com.gniot.parkinglot.dto.response.CommonResponse;
import com.gniot.parkinglot.dto.response.FetchPendingUserApprovalResponse;
import com.gniot.parkinglot.dto.response.PendingUserDetails;
import com.gniot.parkinglot.entity.ParkingLotMaster;
import com.gniot.parkinglot.entity.UserDetails;
import com.gniot.parkinglot.exception.ParkingException;
import com.gniot.parkinglot.service.AdminService;
import com.gniot.parkinglot.util.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ParkingLotMasterRepository parkingLotMasterRepository;

    @Override
    public String createParkingLot(CreateParkingLotRequest parkingLotRequest) {
        log.info("Creating parking lot for request : {}", parkingLotRequest);
        ParkingLotMaster plm = parkingLotMasterRepository.fetchParkingLotMasterByName(parkingLotRequest.getParkingLotName());
        if (plm == null) {
            ParkingLotMaster parkingLotMaster = new ParkingLotMaster();
            parkingLotMaster.setParkingLotName(parkingLotRequest.getParkingLotName());
            parkingLotMaster.setAddress(parkingLotRequest.getAddress());
            parkingLotMaster.setStatus("ACTIVE");
            parkingLotMaster.setBikeCapacity(parkingLotRequest.getBikeCapacity());
            parkingLotMaster.setCarCapacity(parkingLotRequest.getCarCapacity());
            parkingLotMaster.setHeavyVehicleCapacity(parkingLotRequest.getHeavyVehicleCapacity());
            parkingLotMasterRepository.save(parkingLotMaster);
            return "Parking lot created successfully";
        } else {
            return "Parking lot already exists";
        }
    }

    @Override
    public FetchPendingUserApprovalResponse fetchAllPendingApprovalUsers() {
        log.info("Going to fetch all pending approval users from database");
        List<UserDetails> userDetails = userDetailsRepository.fetchAllPendingUser();
        log.info("Total fetched users: {}", userDetails.size());
        return mapToFetchPendingUserApprovalResponse(userDetails);
    }

    @Override
    public FetchPendingUserApprovalResponse fetchACurrentActiveUsers() {
        log.info("Going to fetch current active users from database");
        List<UserDetails> userDetails = userDetailsRepository.fetchAllActiveUser();
        log.info("Total fetched users: {}", userDetails.size());
        return mapToFetchPendingUserApprovalResponse(userDetails);
    }

    @Override
    public CommonResponse approveUser(Long empId) {
        if (empId == null) {
            throw new ParkingException("LX12002", "empId is null");
        }
        UserDetails userDetails = userDetailsRepository.findByEmpId(empId);
        if (userDetails == null) {
            throw new ParkingException("LX12003", "user is not found");
        }
        if (userDetails.getStatus() == Status.ACTIVE)
            throw new ParkingException("LX12004", "user is already active");
        userDetails.setStatus(Status.ACTIVE);
        userDetailsRepository.save(userDetails);
        ParkingLotMaster parkingLotMaster = parkingLotMasterRepository.fetchParkingLotMasterById(userDetails.getParkingLotId());
        if (parkingLotMaster != null) {
            Map<String, String> bodyParam = new HashMap<>();
            bodyParam.put("employee", userDetails.getFullName());
            bodyParam.put("parkingLotName", parkingLotMaster.getParkingLotName());
            bodyParam.put("parkingLotAddress", parkingLotMaster.getAddress());
            bodyParam.put("employeeId", userDetails.getEmployeeId());
            String confirmationEmailBody = EmailHtmlConstants.confirmationEmail;
            confirmationEmailBody = replaceEmailVariable(bodyParam, confirmationEmailBody);
            emailService.sendHtmlEmail(userDetails.getEmailId(), "LxParking | Account Activated", confirmationEmailBody);

        }
        return CommonResponse.builder().message("Employee account approved successfully").build();
    }

    @Override
    public CommonResponse rejectUser(Long empId) {
        log.info("Going to fetch rejected users from database");
        if (empId == null) {
            throw new ParkingException("LX12002", "empId is null");
        }
        UserDetails userDetails = userDetailsRepository.findByEmpId(empId);
        if (userDetails == null) {
            throw new ParkingException("LX12003", "user is not found");
        }
        if (userDetails.getStatus() == Status.REJECTED)
            throw new ParkingException("LX12004", "user is already rejected");
        userDetails.setStatus(Status.REJECTED);
        userDetailsRepository.save(userDetails);
        return CommonResponse.builder().message("Employee account rejected successfully").build();
    }

    @Override
    public CommonResponse disableParking(Long parkingId, String status) {
        log.info("Going to mark parking lot inactive from database");
        List<String> statusList = List.of(Status.ACTIVE.toString(), Status.INACTIVE.toString());
        if (!statusList.contains(status.toUpperCase())) {
            throw new ParkingException("LX12002", "parkingLotStatus is not valid status");
        }
        if (parkingId == null) {
            throw new ParkingException("LX12003", "parking Id is null");
        }
        ParkingLotMaster parkingLotMaster = parkingLotMasterRepository.fetchParkingLotMasterById(parkingId);
        if (parkingLotMaster == null) {
            throw new ParkingException("LX12004", "Parking lot not found");
        }
        if (Objects.equals(parkingLotMaster.getStatus(), status.toUpperCase()))
            throw new ParkingException("LX12005", "Parking lot is already updated");
        parkingLotMaster.setStatus(status.toUpperCase());
        parkingLotMasterRepository.save(parkingLotMaster);
        return CommonResponse.builder().message("Parking Lot status update successfully").build();
    }

    @Override
    public CommonResponse updateParkingLot(UpdateParkingLotRequest updateParkingLotRequest) {
        log.info("Updating parking lot capacity");
        ParkingLotMaster parkingLotMaster = parkingLotMasterRepository.fetchParkingLotMasterById(updateParkingLotRequest.getId());
        if (parkingLotMaster == null) {
            throw new ParkingException("LX12005", "Parking lot not found");
        }
        parkingLotMaster.setBikeCapacity(updateParkingLotRequest.getBikeCapacity());
        parkingLotMaster.setCarCapacity(updateParkingLotRequest.getCarCapacity());
        parkingLotMaster.setHeavyVehicleCapacity(updateParkingLotRequest.getHeavyVehicleCapacity());
        parkingLotMasterRepository.save(parkingLotMaster);
        return CommonResponse.builder().message("Parking Lot capacity update successfully").build();
    }

    private FetchPendingUserApprovalResponse mapToFetchPendingUserApprovalResponse(List<UserDetails> userDetails) {
        FetchPendingUserApprovalResponse response = new FetchPendingUserApprovalResponse();
        List<PendingUserDetails> pendingUserDetailsList = new ArrayList<>();
        for (UserDetails userDetail : userDetails) {
            PendingUserDetails pendingUserDetails = new PendingUserDetails();
            pendingUserDetails.setEmailId(userDetail.getEmailId());
            pendingUserDetails.setEmployeeName(userDetail.getFullName());
            pendingUserDetails.setEmployeeId(userDetail.getEmployeeId());
            pendingUserDetails.setId(userDetail.getId());
            ParkingLotMaster parkingLotMaster = parkingLotMasterRepository.fetchParkingLotMasterById(userDetail.getParkingLotId());
            pendingUserDetails.setParkingLotName(parkingLotMaster.getParkingLotName());
            pendingUserDetails.setParkingLotAddress(parkingLotMaster.getAddress());
            Date createdAt = userDetail.getCreatedAt();
            pendingUserDetails.setRequestedOn(DateFormat.getDateInstance().format(createdAt));
            pendingUserDetailsList.add(pendingUserDetails);
        }
        response.setPendingUserDetails(pendingUserDetailsList);
        return response;
    }

    private static String replaceEmailVariable(Map<String, String> bodyParam, String confirmationEmailBody) {
        return bodyParam.entrySet()
                .stream()
                .reduce(confirmationEmailBody,
                        (str, entry) -> str.replace("{" + entry.getKey() + "}", entry.getValue()),
                        (s1, s2) -> s2);
    }

}
