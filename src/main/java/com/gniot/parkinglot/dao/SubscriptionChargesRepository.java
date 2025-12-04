package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.SubscriptionCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionChargesRepository extends JpaRepository<SubscriptionCharges, Long> {

    @Query("SELECT sc FROM SubscriptionCharges sc WHERE sc.subscriptionName = :subscriptionKey")
    Optional<SubscriptionCharges> findBySubscriptionName(String subscriptionKey);

}
