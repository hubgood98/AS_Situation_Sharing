package com.example.as_situation_sharing_web.service;

import com.example.as_situation_sharing_web.domain.RepairItem;
import com.example.as_situation_sharing_web.exception.RepairItemNotFoundException;
import com.example.as_situation_sharing_web.repository.RepairItemRepository;
import com.example.as_situation_sharing_web.domain.UserData;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RepairItemService {

    private final RepairItemRepository repairItemRepository;

    @Autowired
    public RepairItemService(RepairItemRepository repairItemRepository) {
        this.repairItemRepository = repairItemRepository;
    }

    @Transactional(readOnly = true)
    public List<RepairItem> getRepairItemsByCustomer(UserData userId) {
        return repairItemRepository.findByCustomer(userId);
    }

    @Transactional(readOnly = true)
    public Optional<RepairItem> getRepairItemById(Long id) {
        return repairItemRepository.findById(id);
    }

    //새로운 수리 항목을 생성할때 사용됨 이때 name은 상품 이름
    @Transactional
    public RepairItem createRepairItem(String productName, String description, UserData customer) {
        RepairItem repairItem = RepairItem.builder()
                .name(productName)
                .description(description)
                .customer(customer)
                .createdDate(LocalDateTime.now())
                .currentStep(1)
                .build();
        return repairItemRepository.save(repairItem);
    }

    @Transactional
    public void deleteRepairItem(Long id) {
        if (!repairItemRepository.existsById(id)) {
            throw new RepairItemNotFoundException("Repair Item not found with id: " + id);
        }
        repairItemRepository.deleteById(id);
    }

    //기존 수리항목들을 수정함
    @Transactional
    public RepairItem updateRepairItem(Long id, String name, String description, UserData technician, Integer currentStep) {
        return repairItemRepository.findById(id)
                .map(existingRepairItem -> {
                    RepairItem updatedRepairItem = RepairItem.builder()
                            .id(existingRepairItem.getId())
                            .name(name)
                            .description(description)
                            .customer(existingRepairItem.getCustomer())
                            .technician(technician)
                            .createdDate(existingRepairItem.getCreatedDate()) // 기존 생성 날짜 유지
                            .currentStep(currentStep)
                            .repairSteps(existingRepairItem.getRepairSteps()) // 기존 수리 단계 유지
                            .build();
                    return repairItemRepository.save(updatedRepairItem);
                })
                .orElseThrow(() -> new RuntimeException("Repair Item not found"));
    }
}
