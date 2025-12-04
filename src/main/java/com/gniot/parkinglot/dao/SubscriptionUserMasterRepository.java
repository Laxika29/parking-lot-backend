package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.SubscriptionUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SubscriptionUserMasterRepository extends JpaRepository<SubscriptionUserMaster, Long> {

    @Query("SELECT CASE WHEN COUNT(su) > 0 THEN true ELSE false END " +
           "FROM SubscriptionUserMaster su " +
           "WHERE su.vehicleNumber = :vehicleNumber " +
           "AND su.validTill >= :now")
    boolean checkActiveSubscription(String vehicleNumber, LocalDateTime now);
}
