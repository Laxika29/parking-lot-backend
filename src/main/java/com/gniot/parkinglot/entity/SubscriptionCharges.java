package com.gniot.parkinglot.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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



}
