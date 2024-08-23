package com.example.as_situation_sharing_web.dto.repairItem;

import lombok.Data;

@Data
public class RepairItemRequest {
    private String name;
    private String description;
    private Long customerId;  // 고객 ID만 받아서 처리
    private Long technicianId; // 기술자 ID만 받아서 처리
    private int currentStep;
}
