package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.dto.response.ParkingLotData;
import com.gniot.parkinglot.entity.ParkingLotMaster;
import com.gniot.parkinglot.entity.ParkingRateMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotMasterRepository extends JpaRepository<ParkingLotMaster, Long> {

    @Query("select  pm from ParkingLotMaster pm where pm.id =:id")
    ParkingLotMaster fetchParkingLotMasterById(Long id);

    @Query("select pm from ParkingLotMaster pm where pm.parkingLotName =:parkingLotName")
    ParkingLotMaster fetchParkingLotMasterByName(String parkingLotName);

    @Query("select new com.gniot.parkinglot.dto.response.ParkingLotData(plm.id, plm.parkingLotName, plm.address, plm.bikeCapacity, plm.carCapacity, plm.heavyVehicleCapacity, plm.status) from ParkingLotMaster plm ")
    List<ParkingLotData> findAllParkingLot();

    @Query("select new com.gniot.parkinglot.dto.response.ParkingLotData(plm.id, plm.parkingLotName, plm.address, plm.bikeCapacity, plm.carCapacity, plm.heavyVehicleCapacity, plm.status) from ParkingLotMaster plm where plm.status ='ACTIVE'")
    List<ParkingLotData> findAllActiveParkingLot();

    @Query("select rm from ParkingLotMaster rm where rm.id <> :parkingLotId and rm.status ='ACTIVE'")
    List<ParkingLotMaster> findAllByExcludingParkingId(Long parkingLotId);
}
