package com.gniot.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription_user_master")
public class SubscriptionUserMaster {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO
    )
    private Long id;

    @Column(name = "vehicleNumber")
    private String vehicleNumber;

    @Column(name = "subscriptionId")
    private Long subscriptionId;

    @Column(name = "purchased_on")
    private LocalDateTime purchasedOn;

    @Column(name = "valid_till")
    private LocalDateTime validTill;
}
