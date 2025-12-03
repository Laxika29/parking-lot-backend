package com.gniot.parkinglot.entity;

import com.gniot.parkinglot.constants.ParkingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parking_rate_master")
public class ParkingRateMaster {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "user_type")
    private String userType;// NORMAL, SUBSCRIPTION

    @Column(name = "car_parked_charge")
    private Double carParkingCharge;

    @Column(name = "bike_parked_charge")
    private Double bikeParkingCharge;

    @Column(name = "heavy_vehicle_parked_charge")
    private Double heavyVehicleParkingCharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_type")
    private ParkingType parkingType; //HOURLY, DAILY, MONTHLY
}
