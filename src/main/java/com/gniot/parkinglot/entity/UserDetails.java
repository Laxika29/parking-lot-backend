package com.gniot.parkinglot.entity;


import com.gniot.parkinglot.constants.Status;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String fullName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "employee_id")
    private String employeeId;

    /**
     * plain text password -> hashed password [SHA2 algorithm]
     * SHA2
     * one way encryption
     * plain text -> hashed password [allowed]
     * hashed password -> plain text [not allowed]
     */
    @Column(name = "password")
    private String password;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "role")
    private String role;

    @Column(name = "otp_token")
    private String otpToken;

    @Column(name = "otp_expire_at")
    private LocalDateTime otpExpiryDateTime;

    @Column(name = "is_otp_used")
    private Boolean isOtpUsed;
}

