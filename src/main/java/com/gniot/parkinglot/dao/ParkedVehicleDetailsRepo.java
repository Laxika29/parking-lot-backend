package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.ParkedVehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkedVehicleDetailsRepo extends JpaRepository<ParkedVehicleDetails, Long> {
}
