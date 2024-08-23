package com.example.as_situation_sharing_web.repository;

import com.example.as_situation_sharing_web.domain.RepairItem;
import com.example.as_situation_sharing_web.domain.RepairStep;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepairStepRepository extends JpaRepository<RepairStep, Long> {

    //특정 수리 항목에 속하는 모든 수리 단계들을 찾기위함
    List<RepairStep> findByRepairItem(RepairItem repairItem);
}
