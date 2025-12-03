package com.gniot.parkinglot.entity;


import com.gniot.parkinglot.constants.ParkingType;
import com.gniot.parkinglot.constants.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "parked_vehicle_details")
public class ParkedVehicleDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_type")
    private ParkingType parkingType;

    @Column(name = "entry_date_time")
    private LocalDateTime entryDateTime = LocalDateTime.now();

    @Column(name = "exit_date_time")
    private LocalDateTime exitDateTime;

    @Column(name = "alloted_by")
    private String allotedBy;

    @Column(name = "checkout_by")
    private String checkoutBy;

    @Column(name = "isLocked")
    private Boolean isLocked;

    @Column(name = "lock_reason", columnDefinition = "TEXT")
    private String lockReason;

    @Column(name = "locked_by")
    private String lockedBy;

    @Column(name = "penalty_payment_type")
    private String penaltyPaymentType;

    @Column(name = "locked_on")
    private LocalDateTime lockedOn;

    @Column(name = "lock_penality_charge")
    private Double lockPenaltyCharge;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "parking_charge")
    private Double parkingCharge;

    @Column(name = "parking_status")
    private String parkingStatus;
}
