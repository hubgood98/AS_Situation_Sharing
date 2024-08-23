package com.example.as_situation_sharing_web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class RepairItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserData customer;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private UserData technician;

    private LocalDateTime createdDate;

    private int currentStep;

    @OneToMany(mappedBy = "repairItem", cascade = CascadeType.ALL)
    private List<RepairStep> repairSteps;
}
