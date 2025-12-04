package com.gniot.parkinglot.service.impl;

import com.gniot.parkinglot.configuration.JwtService;
import com.gniot.parkinglot.constants.EmailHtmlConstants;
import com.gniot.parkinglot.constants.Status;
import com.gniot.parkinglot.dao.ParkingLotMasterRepository;
import com.gniot.parkinglot.dao.UserDetailsRepository;
import com.gniot.parkinglot.dto.request.AuthRequest;
import com.gniot.parkinglot.dto.request.RegistrationRequest;
import com.gniot.parkinglot.dto.response.LoginResponse;
import com.gniot.parkinglot.entity.ParkingLotMaster;
import com.gniot.parkinglot.entity.UserDetails;
import com.gniot.parkinglot.exception.ParkingException;
import com.gniot.parkinglot.service.LoginService;
import com.gniot.parkinglot.util.CommonUtil;
import com.gniot.parkinglot.util.EmailService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ParkingLotMasterRepository parkingLotMasterRepository;

    @Autowired
    private JwtService jwtService;

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
            confirmationEmailBody = replaceEmailVariable(bodyParam, confirmationEmailBody);
            emailService.sendHtmlEmail(registrationRequest.getEmailId(), "LxParking | Registration Successful", confirmationEmailBody);
            log.info("User registered successfully with email ID: {}", registrationRequest.getEmailId());
        } catch (Exception e) {
            log.error("Error occurred while registering user: {}", e.getMessage());
            throw new RuntimeException("Error occurred while registering user");
        }


    }

    private static String replaceEmailVariable(Map<String, String> bodyParam, String confirmationEmailBody) {
        return bodyParam.entrySet()
                .stream()
                .reduce(confirmationEmailBody,
                        (str, entry) -> str.replace("{" + entry.getKey() + "}", entry.getValue()),
                        (s1, s2) -> s2);
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new ParkingException("LX1102", "Invalid email");
        }
        UserDetails user = userDetailsRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ParkingException("LX1103", "User not found"));
        if (user.getStatus().equals(Status.PENDING_FOR_APPROVAL)) {
            throw new ParkingException("LX1100", "User not approved yet.");
        }
        if (!request.isOtpValidation()) {
            log.info("Username-Password flow");
            if (user.getPassword().equals(CommonUtil.hashPassword(request.getPassword()))) {
                return generateLoginResponse(user);
            } else
                throw new ParkingException("LX1103", "Invalid password");
        } else if (StringUtils.isEmpty(request.getOtpCode())) {
            log.info("Sent Otp flow");
            sendOtp(user);
            throw new ParkingException("LX001", "SUCCESS", "OTP sent to the email");
        } else {
            if (validateUserOtp(request, user)) {
                log.info("Validated OTP and generate token");
                return generateLoginResponse(user);
            } else {
                throw new ParkingException("LX0001", "Invalid OTP");
            }

        }
    }

    private LoginResponse generateLoginResponse(UserDetails user) {
        String token = jwtService.generateToken(
                user.getEmailId(),
                user.getId(),
                user.getRole(),
                user.getParkingLotId()
        );
        ParkingLotMaster parkingLotMaster = parkingLotMasterRepository.fetchParkingLotMasterById(user.getParkingLotId());
        if (user.getRole().equals("EMPLOYEE") && parkingLotMaster.getStatus().equals(Status.INACTIVE.toString())) {
            throw new ParkingException("LX0001", "Parking Lot not serviceable right now.");
        }

        return LoginResponse.builder()
                .name(user.getFullName())
                .token(token)
                .userId(user.getId())
                .role(user.getRole())
                .parkingLotId(user.getParkingLotId())
                .tokenType("Bearer")
                .employeeId(user.getEmployeeId())
                .parkingLotName(parkingLotMaster.getParkingLotName())
                .build();
    }

    private boolean validateUserOtp(AuthRequest request, UserDetails user) {
        log.info("Validating user OTP");
        String reqOtpHash = CommonUtil.hashPassword(request.getOtpCode());
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(user.getOtpExpiryDateTime()) && Boolean.FALSE.equals(user.getIsOtpUsed())) {
            boolean equals = reqOtpHash.equals(user.getOtpToken());
            if (!equals) {
                return false;
            } else {
                user.setIsOtpUsed(Boolean.TRUE);
                userDetailsRepository.save(user);
                return true;
            }
        } else {
            throw new ParkingException("LX0002", "Expired/Invalid OTP");
        }
    }

    private void sendOtp(UserDetails user) {
        String otp = generateOtp();
        user.setOtpToken(CommonUtil.hashPassword(otp));
        user.setOtpExpiryDateTime(LocalDateTime.now().plusMinutes(10));
        user.setIsOtpUsed(Boolean.FALSE);
        userDetailsRepository.save(user);
        Map<String, String> bodyParam = new HashMap<>();
        bodyParam.put("employee", user.getFullName());
        bodyParam.put("otp", otp);
        bodyParam.put("expiryMinutes", "15");
        String otpEmail = EmailHtmlConstants.otpEmail;
        otpEmail = replaceEmailVariable(bodyParam, otpEmail);
        emailService.sendHtmlEmail(user.getEmailId(), "LxParking | OTP Authentication", otpEmail);
        log.info("OTP successfully Sent to email ID: {}", user.getEmailId());
    }

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String generateOtp() {
        int number = 100000 + SECURE_RANDOM.nextInt(900000); // 100000–999999
        return String.valueOf(number);
    }
}
