package com.gniot.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "parkinglot_master")
public class ParkingLotMaster {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(name="parking_lot_name")
    private String parkingLotName;

    @Column(name="parking_lot_address")
    private String address;

    @Column(name="bike_capacity")
    private Long bikeCapacity;

    @Column(name="car_capacity")
    private Long carCapacity;

    @Column(name="heavy_vehicle_capacity")
    private Long heavyVehicleCapacity;

    @Column(name="status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}
