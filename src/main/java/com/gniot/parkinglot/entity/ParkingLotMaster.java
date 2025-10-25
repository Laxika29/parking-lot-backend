package com.gniot.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parkinglot_master")
public class ParkingLotMaster {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="parking_lot_name")
    private String parkingLotName;

    @Column(name="parking_lot_address")
    private String address;

    @Column(name="bike_capacity")
    private int bikeCapacity;

    @Column(name="car_capacity")
    private int carCapacity;

    @Column(name="heavy_vehicle_caacity")
    private int heavyVehicleCapacity;

}
