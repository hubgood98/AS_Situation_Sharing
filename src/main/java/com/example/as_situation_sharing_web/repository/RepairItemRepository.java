package com.example.as_situation_sharing_web.repository;

import com.example.as_situation_sharing_web.domain.RepairItem;
import com.example.as_situation_sharing_web.domain.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepairItemRepository extends JpaRepository<RepairItem, Long> {
    //특정 고객이 맡긴 수리 항목들을 모두 찾음
    List<RepairItem> findByCustomer(UserData userId);
    List<RepairItem> findByTechnician(UserData technician);
}
