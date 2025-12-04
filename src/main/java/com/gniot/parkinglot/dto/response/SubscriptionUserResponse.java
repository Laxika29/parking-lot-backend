package com.gniot.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubscriptionUserResponse {
    private List<SubscriptionUserInfo> subscriptionUserInfoList;
}
