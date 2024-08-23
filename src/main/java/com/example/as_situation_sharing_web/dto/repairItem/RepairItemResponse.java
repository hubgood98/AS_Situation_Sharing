package com.example.as_situation_sharing_web.dto.repairItem;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RepairItemResponse {
    private Long id;
    private String name;
    private String description;
    private String customerName;  // 고객의 이름을 반환
    private String technicianName; // 기술자의 이름을 반환
    private LocalDateTime createdDate;
    private int currentStep;
}
