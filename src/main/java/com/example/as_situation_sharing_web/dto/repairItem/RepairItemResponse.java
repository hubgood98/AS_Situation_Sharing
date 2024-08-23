package com.example.as_situation_sharing_web.dto.repairItem;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RepairItemResponse {
    private Long id;
    private String name;
    private String description;
    private String customerName;  // 고객의 이름을 반환
    private String technicianName; // 기술자의 이름을 반환
    private LocalDateTime createdDate;
    private int currentStep;
}
