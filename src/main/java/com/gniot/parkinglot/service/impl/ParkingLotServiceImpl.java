package com.gniot.parkinglot.service.impl;

import com.gniot.parkinglot.dao.ParkingLotMasterRepository;
import com.gniot.parkinglot.dto.response.FetchParkingLotResponse;
import com.gniot.parkinglot.dto.response.ParkingLotData;
import com.gniot.parkinglot.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotMasterRepository parkingLotMasterRepository;

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
}
