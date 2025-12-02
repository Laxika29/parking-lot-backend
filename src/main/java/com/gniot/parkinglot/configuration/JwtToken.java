package com.gniot.parkinglot.configuration;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JwtToken {
    private Long id;
    private LocalDateTime createTime;
    private String role;
    private LocalDateTime expireTime;
    private Long parkingLotId;
}
