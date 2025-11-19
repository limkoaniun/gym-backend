package com.example.serving_web_content.repository;

import com.example.serving_web_content.model.UsageStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsageStepRepository extends JpaRepository<UsageStep, Long> {
    List<UsageStep> findByEquipmentUsageId(Long usageId);
}
