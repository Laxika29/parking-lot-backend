package com.gniot.parkinglot.service.impl;

import com.gniot.parkinglot.constants.EmailHtmlConstants;
import com.gniot.parkinglot.constants.Status;
import com.gniot.parkinglot.dao.UserDetailsRepository;
import com.gniot.parkinglot.dto.request.RegistrationRequest;
import com.gniot.parkinglot.entity.UserDetails;
import com.gniot.parkinglot.service.LoginService;
import com.gniot.parkinglot.util.CommonUtil;
import com.gniot.parkinglot.util.EmailService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {
        log.info("User registration request received {}", registrationRequest);

        // validate with database : email id is already registered or not
        UserDetails userDetails = userDetailsRepository.checkExistingEmail(registrationRequest.getEmailId());

        if (userDetails != null) {
            if (Status.ACTIVE.equals(userDetails.getStatus())) {
                log.error("Email ID already registered: {}", registrationRequest.getEmailId());
                throw new RuntimeException("Email ID already registered");
            } else if (Status.PENDING_FOR_APPROVAL.equals(userDetails.getStatus())) {
                log.error("User registration is still pending for approval: {}", registrationRequest.getEmailId());
                throw new RuntimeException("User registration is still pending for approval");
            }
        }

        log.info("Registering new user with email ID: {}", registrationRequest.getEmailId());
        UserDetails newUser = new UserDetails();
        newUser.setFullName(registrationRequest.getFullName());
        newUser.setEmailId(registrationRequest.getEmailId());
        newUser.setParkingLotId(registrationRequest.getParkingLotId());
        newUser.setStatus(Status.PENDING_FOR_APPROVAL);
        newUser.setEmployeeId(CommonUtil.generateEmployeeId());
        newUser.setRole("EMPLOYEE");
        try {
            newUser.setPassword(CommonUtil.hashPassword(registrationRequest.getPassword()));
            userDetailsRepository.save(newUser);
            Map<String, String> bodyParam = new HashMap<>();
            bodyParam.put("employee", registrationRequest.getFullName());
            bodyParam.put("employeeId", newUser.getEmployeeId());
            String confirmationEmailBody = EmailHtmlConstants.registrationEmail;
            confirmationEmailBody = bodyParam.entrySet()
                    .stream()
                    .reduce(confirmationEmailBody,
                            (str, entry) -> str.replace("{" + entry.getKey() + "}", entry.getValue()),
                            (s1, s2) -> s2);
            emailService.sendHtmlEmail(registrationRequest.getEmailId(), "LxParking | Registration Successful", confirmationEmailBody);
            log.info("User registered successfully with email ID: {}", registrationRequest.getEmailId());
        } catch (Exception e) {
            log.error("Error occurred while registering user: {}", e.getMessage());
            throw new RuntimeException("Error occurred while registering user");
        }


    }
}
