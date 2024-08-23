package com.example.as_situation_sharing_web.dto.repairItem;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RepairItemRequest {
    private String name;
    private String description;
    private String customerId;  // 고객 ID만 받아서 처리
    private String technicianId; // 기술자 ID만 받아서 처리
    private int currentStep;
}
