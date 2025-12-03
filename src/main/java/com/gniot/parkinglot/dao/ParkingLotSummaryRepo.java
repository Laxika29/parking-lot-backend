package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.ParkingLotSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotSummaryRepo extends JpaRepository<ParkingLotSummary, Long> {
    @Modifying
    @Query("update ParkingLotSummary set carParkedCount = (carParkedCount + :value) where parkingLotId =:parkingLotId")
    void updateCarCount(Long parkingLotId, int value);

    @Modifying
    @Query("update ParkingLotSummary set bikeParkedCount = (bikeParkedCount + :value) where parkingLotId =:parkingLotId")
    void updateBikeCount(Long parkingLotId, int value);

    @Modifying
    @Query("update ParkingLotSummary set heavyVehicleParkedCount = (heavyVehicleParkedCount + :value) where parkingLotId =:parkingLotId")
    void updateHeavyVehicleCount(Long parkingLotId, int value);
}
