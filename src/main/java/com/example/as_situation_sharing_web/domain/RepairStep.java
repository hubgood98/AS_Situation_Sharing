package com.example.as_situation_sharing_web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class RepairStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "repairItem_id", nullable = false)
    private RepairItem repairItem;
    private String stepName;
    private String description;
    private String imageUrl;
    private LocalDateTime completedDate;
    private Boolean isCompleted;
}
