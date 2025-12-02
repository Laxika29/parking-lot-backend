package com.gniot.parkinglot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FetchPendingUserApprovalResponse {
    private List<PendingUserDetails> pendingUserDetails;
}
