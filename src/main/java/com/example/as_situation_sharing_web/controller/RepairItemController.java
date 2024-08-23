package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.domain.RepairItem;
import com.example.as_situation_sharing_web.domain.UserData;
import com.example.as_situation_sharing_web.dto.repairItem.RepairItemRequest;
import com.example.as_situation_sharing_web.dto.repairItem.RepairItemResponse;
import com.example.as_situation_sharing_web.service.RepairItemService;
import com.example.as_situation_sharing_web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Tag(name = "RepairItem 관리", description = "수리 항목의 생성, 조회, 수정, 삭제를 관리하는 API")
@RequestMapping("/api/repair")
public class RepairItemController {

    private final RepairItemService repairItemService;
    private final UserService userService;

    // 특정 고객이 맡긴 수리 항목 리스트 조회
    @GetMapping("/list")
    @Operation(summary = "수리 항목 리스트 조회", description = "특정 고객이 맡긴 모든 수리 항목의 리스트를 조회합니다.")
    public ResponseEntity<List<RepairItemResponse>> getRepairItems(
            @Parameter(description = "고객의 ID", example = "1") @RequestParam String customerId) {

        UserData customer = userService.getUserByUserId(customerId);
        List<RepairItem> repairItems = repairItemService.getRepairItemsByCustomer(customer);

        List<RepairItemResponse> response = repairItems.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // 수리 항목 상세 조회
    @GetMapping("/{id}")
    @Operation(summary = "수리 항목 상세 조회", description = "주어진 ID에 해당하는 수리 항목의 상세 정보를 조회합니다.")
    public ResponseEntity<RepairItemResponse> getRepairItem(
            @Parameter(description = "수리 항목의 ID", example = "1") @PathVariable Long id) {

        Optional<RepairItem> repairItem = repairItemService.getRepairItemById(id);

        return repairItem.map(item -> ResponseEntity.ok(convertToResponse(item)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 새로운 수리 항목 생성
    @PostMapping("/")
    @Operation(summary = "수리 항목 생성", description = "새로운 수리 항목을 생성합니다.")
    public ResponseEntity<RepairItemResponse> createRepairItem(
            @RequestBody RepairItemRequest repairItemRequest) {

        UserData customer = userService.getUserByUserId(repairItemRequest.getCustomerId());
        UserData technician = null;

        if (repairItemRequest.getTechnicianId() != null) {
            technician = userService.getUserByUserId(repairItemRequest.getTechnicianId());
        }

        RepairItem repairItem = repairItemService.createRepairItem(
                repairItemRequest.getName(),
                repairItemRequest.getDescription(),
                customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(repairItem));
    }

    // 수리 항목 업데이트
    @PutMapping("/{id}")
    @Operation(summary = "수리 항목 업데이트", description = "주어진 ID에 해당하는 수리 항목의 정보를 업데이트합니다.")
    public ResponseEntity<RepairItemResponse> updateRepairItem(
            @Parameter(description = "수리 항목의 ID", example = "1") @PathVariable Long id,
            @RequestBody RepairItemRequest repairItemRequest) {

        UserData technician = null;

        if (repairItemRequest.getTechnicianId() != null) {
            technician = userService.getUserByUserId(repairItemRequest.getTechnicianId());
        }

        RepairItem updatedItem = repairItemService.updateRepairItem(
                id,
                repairItemRequest.getName(),
                repairItemRequest.getDescription(),
                technician,
                repairItemRequest.getCurrentStep());

        return ResponseEntity.ok(convertToResponse(updatedItem));
    }

    // 수리 항목 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "수리 항목 삭제", description = "주어진 ID에 해당하는 수리 항목을 삭제합니다.")
    public ResponseEntity<Void> deleteRepairItem(
            @Parameter(description = "수리 항목의 ID", example = "1") @PathVariable Long id) {

        repairItemService.deleteRepairItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // RepairItem -> RepairItemResponse 변환 메서드
    private RepairItemResponse convertToResponse(RepairItem repairItem) {
        return RepairItemResponse.builder()
                .id(repairItem.getId())
                .name(repairItem.getName())
                .description(repairItem.getDescription())
                .customerName(repairItem.getCustomer().getUsername())
                .technicianName(repairItem.getTechnician() != null ? repairItem.getTechnician().getUsername() : null)
                .createdDate(repairItem.getCreatedDate())
                .currentStep(repairItem.getCurrentStep())
                .build();
    }
}
