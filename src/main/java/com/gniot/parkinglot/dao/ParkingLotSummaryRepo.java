package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.ParkingLotSummary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLotSummaryRepo extends JpaRepository<ParkingLotSummary, Long> {
    @Modifying
    @Transactional
    @Query("update ParkingLotSummary set carParkedCount = (carParkedCount + :value) where parkingLotId =:parkingLotId")
    int updateCarCount(Long parkingLotId, int value);

    @Modifying
    @Transactional
    @Query("update ParkingLotSummary set bikeParkedCount = (bikeParkedCount + :value) where parkingLotId =:parkingLotId")
    int updateBikeCount(Long parkingLotId, int value);

    @Modifying
    @Transactional
    @Query("update ParkingLotSummary set heavyVehicleParkedCount = (heavyVehicleParkedCount + :value) where parkingLotId =:parkingLotId")
    int updateHeavyVehicleCount(Long parkingLotId, int value);

    @Query("select case when count(p) > 0 then true else false end from ParkingLotSummary p where p.parkingLotId = :parkingLotId")
    boolean existsByParkingLotId(Long parkingLotId);

    @Query("select p from ParkingLotSummary p where p.parkingLotId = :parkingLotId")
    Optional<ParkingLotSummary> findByParkingLotId(Long parkingLotId);
}
