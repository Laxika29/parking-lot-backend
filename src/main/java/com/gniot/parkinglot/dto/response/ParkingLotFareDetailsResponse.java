package com.gniot.parkinglot.dto.response;

import com.gniot.parkinglot.dto.request.ParkingFareDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ParkingLotFareDetailsResponse {
    private List<ParkingFareDetails> parkingFareDetailsList;


}
