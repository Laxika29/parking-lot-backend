package com.gniot.parkinglot.entity;


import com.gniot.parkinglot.constants.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subscription_charges")
public class SubscriptionCharges {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO
    )
    private Long id;

    @Column(name = "subscription_name")
    private String subscriptionName; // vehicleType_subscriptionFrequency_subscriptionType

    @Column(name = "subscription_frequency")
    private String subscriptionFrequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "subscription_charge")
    private Double subscriptionCharge;

    @Column(name = "subscription_type")
    private String subscriptionType;
}
