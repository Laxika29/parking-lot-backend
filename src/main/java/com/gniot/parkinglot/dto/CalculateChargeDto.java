package com.gniot.parkinglot.dto;

import com.gniot.parkinglot.constants.ParkingType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalculateChargeDto {
    private long hourCount;
    private long dayCount;
    private long weekCount;
    private long monthCount;
    private List<ParkingType> parkingTypeList = new ArrayList<>();
}
