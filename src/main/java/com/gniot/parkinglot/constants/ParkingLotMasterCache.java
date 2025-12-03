package com.gniot.parkinglot.constants;

import com.gniot.parkinglot.dao.ParkingLotMasterRepository;
import com.gniot.parkinglot.entity.ParkingLotMaster;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParkingLotMasterCache {
    public static Map<Long, ParkingLotMaster> parkingLotMasterMap = new HashMap<>();
    @Autowired
    private ParkingLotMasterRepository parkingLotMasterRepository;

    @PostConstruct
    private void ParkingLotMasterCache() {
        List<ParkingLotMaster> all = parkingLotMasterRepository.findAll();
        for (ParkingLotMaster parkingLotMaster : all) {
            parkingLotMasterMap.put(parkingLotMaster.getId(), parkingLotMaster);
        }
    }
}
