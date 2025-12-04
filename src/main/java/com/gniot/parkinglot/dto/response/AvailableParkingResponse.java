package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AvailableParkingResponse {
    private List<AvailableParkingInfo> availableParkingInfoList;

}
