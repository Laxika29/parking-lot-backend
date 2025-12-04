package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.ParkingRateMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRateMasterRepo extends JpaRepository<ParkingRateMaster, Long> {
    @Query("select rm from ParkingRateMaster rm where rm.parkingLotId=:parkingLotId")
    List<ParkingRateMaster> findAllById(String parkingLotId);

    @Query("select rm from ParkingRateMaster rm where rm.parkingLotId=:parkingLotId")
    List<ParkingRateMaster> findAllByParkingLotId(Long parkingLotId);
}
