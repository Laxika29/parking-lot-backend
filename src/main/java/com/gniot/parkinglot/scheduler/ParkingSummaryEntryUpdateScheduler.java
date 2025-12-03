package com.gniot.parkinglot.scheduler;

import com.gniot.parkinglot.dao.ParkingLotMasterRepository;
import com.gniot.parkinglot.dao.ParkingLotSummaryRepo;
import com.gniot.parkinglot.entity.ParkingLotMaster;
import com.gniot.parkinglot.entity.ParkingLotSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ParkingSummaryEntryUpdateScheduler {
    @Autowired
    private ParkingLotSummaryRepo parkingLotSummaryRepo;

    @Autowired
    private ParkingLotMasterRepository parkingLotMasterRepository;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateParkingSummaryEntries() {
        log.info("Starting Parking Summary Entry Update Scheduler");
        List<ParkingLotMaster> parkingLotMasterList = parkingLotMasterRepository.findAll();
        for (ParkingLotMaster parkingLotMaster : parkingLotMasterList) {
            Long parkingLotId = parkingLotMaster.getId();
            boolean exists = parkingLotSummaryRepo.existsByParkingLotId(parkingLotId);
            if (!exists) {
                log.info("Creating summary entry for Parking Lot ID: {}", parkingLotId);
                ParkingLotSummary summary = new ParkingLotSummary();
                summary.setParkingLotId(parkingLotId);
                summary.setCarParkedCount(0L);
                summary.setBikeParkedCount(0L);
                summary.setHeavyVehicleParkedCount(0L);
                parkingLotSummaryRepo.save(summary);
            }
        }

    }
}
