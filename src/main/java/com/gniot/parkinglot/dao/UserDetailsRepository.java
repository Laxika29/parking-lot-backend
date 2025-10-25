package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {


    @Query("SELECT u FROM UserDetails u WHERE u.emailId = :emailId")
    UserDetails checkExistingEmail(String emailId);
}
