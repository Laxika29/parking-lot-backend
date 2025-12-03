package com.gniot.parkinglot.service.impl;

import com.gniot.parkinglot.constants.AppConstants;
import com.gniot.parkinglot.constants.ParkingType;
import com.gniot.parkinglot.constants.VehicleType;
import com.gniot.parkinglot.dao.ParkedVehicleDetailsRepo;
import com.gniot.parkinglot.dao.ParkingLotMasterRepository;
import com.gniot.parkinglot.dao.ParkingLotSummaryRepo;
import com.gniot.parkinglot.dao.ParkingRateMasterRepo;
import com.gniot.parkinglot.dto.CalculateChargeDto;
import com.gniot.parkinglot.dto.request.CheckinParkingLotReq;
import com.gniot.parkinglot.dto.request.ParkingLotPaymentReq;
import com.gniot.parkinglot.dto.response.CheckoutVehicleResponse;
import com.gniot.parkinglot.dto.response.CommonResponse;
import com.gniot.parkinglot.dto.response.FetchParkingLotResponse;
import com.gniot.parkinglot.dto.response.ParkingLotData;
import com.gniot.parkinglot.entity.ParkedVehicleDetails;
import com.gniot.parkinglot.entity.ParkingLotSummary;
import com.gniot.parkinglot.entity.ParkingRateMaster;
import com.gniot.parkinglot.exception.ParkingException;
import com.gniot.parkinglot.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotMasterRepository parkingLotMasterRepository;

    @Autowired
    private ParkedVehicleDetailsRepo parkedVehicleDetailsRepo;

    @Autowired
    private ParkingLotSummaryRepo parkingLotSummaryRepo;

    @Autowired
    private ParkingRateMasterRepo parkingRateMasterRepo;

    @Override
    public FetchParkingLotResponse fetchAllParkingLot() {
        log.info("Fetching all parking lots");
        List<ParkingLotData> parkingLotDataList = parkingLotMasterRepository.findAllParkingLot();
        FetchParkingLotResponse fetchParkingLotResponse = new FetchParkingLotResponse();
        fetchParkingLotResponse.setParkingLots(parkingLotDataList);
        return fetchParkingLotResponse;
    }

    @Override
    public FetchParkingLotResponse fetchAllActiveParkingLot() {
        log.info("Fetching all parking lots");
        List<ParkingLotData> parkingLotDataList = parkingLotMasterRepository.findAllActiveParkingLot();
        FetchParkingLotResponse fetchParkingLotResponse = new FetchParkingLotResponse();
        fetchParkingLotResponse.setParkingLots(parkingLotDataList);
        return fetchParkingLotResponse;
    }

    @Override
    public CommonResponse checkInVehicle(CheckinParkingLotReq request) {
        log.info("Checking in vehicle");
        validateRequestForCheckIn(request);
        VehicleType vehicleType = VehicleType.getVehicleType(request.getVehicleType().toUpperCase());
        ParkingType parkingType = ParkingType.getParkingType(request.getParkingType().toUpperCase());
        updateParkingLotSummary(vehicleType, 1);
        ParkedVehicleDetails parkedVehicleDetails = new ParkedVehicleDetails();
        parkedVehicleDetails.setAllotedBy(MDC.get("username"));
        parkedVehicleDetails.setParkingLotId(Long.valueOf(MDC.get("parkingLotId")));
        parkedVehicleDetails.setVehicleNumber(request.getVehicleNumber());
        parkedVehicleDetails.setParkingType(parkingType);
        parkedVehicleDetails.setVehicleType(vehicleType);
        parkedVehicleDetails.setParkingStatus("PARKED");
        parkedVehicleDetailsRepo.save(parkedVehicleDetails);
        return CommonResponse.builder().message("Vehicle checkin has been done").build();
    }


    @Override
    public CheckoutVehicleResponse checkOutVehicle(CheckinParkingLotReq request) {
        log.info("Checking in vehicle");
        if (request.getParkingId() != null) {
            Optional<ParkedVehicleDetails> parkedVehicleDetailsOptional = parkedVehicleDetailsRepo.findById(request.getParkingId());
            if (parkedVehicleDetailsOptional.isPresent()) {
                ParkedVehicleDetails parkedVehicleDetails = parkedVehicleDetailsOptional.get();
                updateParkingLotSummary(parkedVehicleDetails.getVehicleType(), -1);
                calculateRate(parkedVehicleDetails);
                parkedVehicleDetails.setParkingStatus("NOT PARKED");
                parkedVehicleDetailsRepo.save(parkedVehicleDetails);
                return CheckoutVehicleResponse.builder()
                        .parkingId(request.getParkingId())
                        .parkingCharge(parkedVehicleDetails.getParkingCharge())
                        .entryTime(parkedVehicleDetails.getEntryDateTime())
                        .exitTime(parkedVehicleDetails.getExitDateTime()).build();
            }

        }
        throw new ParkingException("Invalid parking id");
    }

    @Override
    public CommonResponse confirmPayment(ParkingLotPaymentReq request) {
        log.info("Confirm payment request");
        Optional<ParkedVehicleDetails> parkedVehicleDetailsOptional = parkedVehicleDetailsRepo.findById(Long.valueOf(MDC.get(AppConstants.PARKING_LOT_ID)));
        if (Objects.equals(request.getPaymentFor(), "PENALTY")) {
            if (parkedVehicleDetailsOptional.isPresent()) {
                ParkedVehicleDetails parkedVehicleDetails = parkedVehicleDetailsOptional.get();
                parkedVehicleDetails.setPenaltyPaymentType(request.getPaymentMode());
                parkedVehicleDetails.setIsLocked(false);
                parkedVehicleDetailsRepo.save(parkedVehicleDetails);
            }
        } else if (Objects.equals(request.getPaymentFor(), "PARKING")) {
            if (parkedVehicleDetailsOptional.isPresent()) {
                ParkedVehicleDetails parkedVehicleDetails = parkedVehicleDetailsOptional.get();
                parkedVehicleDetails.setPaymentType(request.getPaymentMode());
                parkedVehicleDetails.setIsLocked(false);
                parkedVehicleDetails.setParkingStatus("NOT PARKED");
                parkedVehicleDetailsRepo.save(parkedVehicleDetails);
            }
        } else if (Objects.equals(request.getPaymentFor(), "SUBSCRIPTION")) {

        }
        return CommonResponse.builder().message("Payment confirmed!!").build();
    }

    private void calculateRate(ParkedVehicleDetails parkedVehicleDetails) {
        LocalDateTime now = LocalDateTime.now();
        parkedVehicleDetails.setEntryDateTime(now);
        long hours = Duration.between(parkedVehicleDetails.getEntryDateTime(), now).toHours();
        List<ParkingRateMaster> parkingLotId = parkingRateMasterRepo.findAllById(MDC.get("parkingLotId"));
        Map<ParkingType, ParkingRateMaster> rateChargeMap =
                parkingLotId.stream()
                        .filter(v -> Objects.equals(v.getUserType(), "NORMAL"))
                        .collect(Collectors.toMap(ParkingRateMaster::getParkingType, v -> v));
        CalculateChargeDto chargeDto = new CalculateChargeDto();
        chargeDto.setParkingTypeList(new ArrayList<>(rateChargeMap.keySet()));
        calculateTotalCountParkingTypeWise(chargeDto, hours);
        calculateTotalFare(chargeDto, rateChargeMap, parkedVehicleDetails);
    }

    private void calculateTotalFare(CalculateChargeDto chargeDto, Map<ParkingType, ParkingRateMaster> rateChargeMap, ParkedVehicleDetails parkedVehicleDetails) {
        Double totalFare = 0.0;
        if (chargeDto.getMonthCount() > 0)
            totalFare += getFare(rateChargeMap.get(ParkingType.MONTHLY), parkedVehicleDetails.getVehicleType());
        if (chargeDto.getWeekCount() > 0)
            totalFare += getFare(rateChargeMap.get(ParkingType.WEEKLY), parkedVehicleDetails.getVehicleType());
        if (chargeDto.getDayCount() > 0)
            totalFare += getFare(rateChargeMap.get(ParkingType.DAILY), parkedVehicleDetails.getVehicleType());
        if (chargeDto.getHourCount() > 0)
            totalFare += getFare(rateChargeMap.get(ParkingType.HOURLY), parkedVehicleDetails.getVehicleType());
        parkedVehicleDetails.setParkingCharge(totalFare);
    }

    private Double getFare(ParkingRateMaster parkingRateMaster, VehicleType vehicleType) {
        switch (vehicleType) {
            case CAR:
                return parkingRateMaster.getCarParkingCharge();
            case BIKE:
                return parkingRateMaster.getBikeParkingCharge();
            case HEAVY_VEHICLE:
                return parkingRateMaster.getHeavyVehicleParkingCharge();
            default:
                return 0.0;
        }
    }

    private void calculateTotalCountParkingTypeWise(CalculateChargeDto chargeDto, long hours) {
        if (chargeDto.getParkingTypeList().contains(ParkingType.MONTHLY)) {
            long total = hours / (24 * 30);
            if (total > 0) {
                chargeDto.setMonthCount(total);
                hours = hours % (24 * 30);
            }
        }

        if (chargeDto.getParkingTypeList().contains(ParkingType.WEEKLY)) {
            long total = hours / (24 * 7);
            if (total > 0) {
                chargeDto.setWeekCount(total);
                hours = hours % (24 * 7);
            }
        }

        if (chargeDto.getParkingTypeList().contains(ParkingType.DAILY)) {
            long total = hours / (24);
            if (total > 0) {
                chargeDto.setDayCount(total);
                hours = hours % (24);
            }
        }

        if (chargeDto.getParkingTypeList().contains(ParkingType.HOURLY)) {
            chargeDto.setHourCount(hours);
        }
    }

    private void updateParkingLotSummary(VehicleType vehicleType, int value) {
        switch (vehicleType) {
            case CAR:
                parkingLotSummaryRepo.updateCarCount(Long.valueOf(MDC.get("parkingLotId")), value);
                return;
            case BIKE:
                parkingLotSummaryRepo.updateBikeCount(Long.valueOf(MDC.get("parkingLotId")), value);
                return;
            case HEAVY_VEHICLE:
                parkingLotSummaryRepo.updateHeavyVehicleCount(Long.valueOf(MDC.get("parkingLotId")), value);
                return;
            default:
                break;
        }
    }

    private void validateRequestForCheckIn(CheckinParkingLotReq request) {
        if (!StringUtils.hasLength(request.getParkingType()))
            throw new ParkingException("Parking type is required");
        if (!StringUtils.hasLength(request.getVehicleType()))
            throw new ParkingException("Vehicle type is required");
        if (!StringUtils.hasLength(request.getVehicleNumber()))
            throw new ParkingException("Vehicle number is required");
    }
}
