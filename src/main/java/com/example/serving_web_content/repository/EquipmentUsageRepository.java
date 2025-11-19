package com.example.serving_web_content.repository;

import com.example.serving_web_content.model.EquipmentUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentUsageRepository extends JpaRepository<EquipmentUsage, Long> {
    List<EquipmentUsage> findByEquipmentId(Long equipmentId);
}
