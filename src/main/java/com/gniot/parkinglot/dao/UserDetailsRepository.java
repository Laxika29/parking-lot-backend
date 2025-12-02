package com.gniot.parkinglot.dao;

import com.gniot.parkinglot.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {


    @Query("SELECT u FROM UserDetails u WHERE u.emailId = :emailId")
    UserDetails checkExistingEmail(String emailId);

    @Query("select us from UserDetails us where us.status = 'PENDING_FOR_APPROVAL' and us.role = 'EMPLOYEE'")
    List<UserDetails> fetchAllPendingUser();

    @Query("select ud from UserDetails ud where ud.emailId=:email")
    Optional<UserDetails> findByEmail(String email);

    @Query("select us from UserDetails us where us.status = 'ACTIVE' and us.role = 'EMPLOYEE'")
    List<UserDetails> fetchAllActiveUser();

    @Query("SELECT u FROM UserDetails u WHERE u.id = :empId")
    UserDetails findByEmpId(Long empId);
}
