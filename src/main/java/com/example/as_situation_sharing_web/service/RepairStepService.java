package com.example.as_situation_sharing_web.service;

import com.example.as_situation_sharing_web.domain.RepairItem;
import com.example.as_situation_sharing_web.domain.RepairStep;
import com.example.as_situation_sharing_web.repository.RepairStepRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RepairStepService {

    private final RepairStepRepository repairStepRepository;

    @Autowired
    public RepairStepService(RepairStepRepository repairStepRepository) {
        this.repairStepRepository = repairStepRepository;
    }

    @Transactional(readOnly = true)
    public List<RepairStep> getRepairStepsByRepairItem(RepairItem repairItem) {
        return repairStepRepository.findByRepairItem(repairItem);
    }

    @Transactional(readOnly = true)
    public Optional<RepairStep> getRepairStepById(Long id) {
        return repairStepRepository.findById(id);
    }

    //새로운 수리 단계를 생성할 때 필요한 필드들을 인자로 받아 RepairStep 객체 생성
    @Transactional
    public RepairStep createRepairStep(String stepName, String description, String imageUrl, RepairItem repairItem) {
        RepairStep repairStep = RepairStep.builder()
                .stepName(stepName)
                .description(description)
                .imageUrl(imageUrl)
                .repairItem(repairItem)
                .isCompleted(false)  // 초기 상태는 완료되지 않음
                .build();
        return repairStepRepository.save(repairStep);
    }

    @Transactional
    public RepairStep updateRepairStep(Long id, String stepName, String description, String imageUrl, Boolean isCompleted) {
        return repairStepRepository.findById(id)
                .map(existingRepairStep -> {
                    RepairStep updatedRepairStep = RepairStep.builder()
                            .id(existingRepairStep.getId())
                            .stepName(stepName)
                            .description(description)
                            .imageUrl(imageUrl)
                            .repairItem(existingRepairStep.getRepairItem()) // 기존 RepairItem 유지
                            .isCompleted(isCompleted)
                            .completedDate(isCompleted ? LocalDateTime.now() : existingRepairStep.getCompletedDate()) // 완료 여부에 따라 날짜 설정
                            .build();
                    return repairStepRepository.save(updatedRepairStep);
                })
                .orElseThrow(() -> new RuntimeException("Repair Step not found"));
    }

    @Transactional
    public void deleteRepairStep(Long id) {
        repairStepRepository.deleteById(id);
    }
}
