package com.gniot.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parking_lot_summary")
public class ParkingLotSummary {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "bike_parked_count", columnDefinition = "default 0")
    private Long bikeParkedCount;

    @Column(name = "car_parked_count", columnDefinition = "default 0")
    private Long carParkedCount;

    @Column(name = "heavy_vehicle_parked_count", columnDefinition = "default 0")
    private Long heavyVehicleParkedCount;
}
