package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.ParkedVehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkedVehicleDetailsRepo extends JpaRepository<ParkedVehicleDetails, Long> {
    @Query("select case when count(p) > 0 then true else false end from ParkedVehicleDetails p where p.vehicleNumber = :vehicleNumber and p.parkingStatus = 'PARKED'")
    boolean checkForVehicle(String vehicleNumber);

    @Query("select p from ParkedVehicleDetails p where p.parkingLotId = :parkingLotId and p.parkingStatus = 'PARKED'")
    List<ParkedVehicleDetails> fetchAllParkedVehicles(Long parkingLotId);
}
