package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Equipment;
import com.example.serving_web_content.model.EquipmentUsage;
import com.example.serving_web_content.model.UsageStep;
import com.example.serving_web_content.repository.EquipmentUsageRepository;
import com.example.serving_web_content.repository.UsageStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usages")
public class UsageController {
    @Autowired
    private EquipmentUsageRepository usageRepository;
    @Autowired
    private UsageStepRepository usageStepRepository;

    @PutMapping("/{usageId}")
    public EquipmentUsage replaceUsage(@RequestBody EquipmentUsage newUsage, @PathVariable Long usageId){
        Optional<EquipmentUsage> usageOptional=usageRepository.findById(usageId);
        if(usageOptional.isPresent()){
            EquipmentUsage oldUsage=usageOptional.get();
            oldUsage.setName(newUsage.getName());
            oldUsage.setDescription(newUsage.getDescription());
            oldUsage.setEquipment(newUsage.getEquipment());
            oldUsage.setMuscles(newUsage.getMuscles());
            usageRepository.save(oldUsage);
            return oldUsage;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment Usage not found");
    }

    @DeleteMapping("/{usageId}")
    void deleteUsage(@PathVariable Long usageId){
        usageRepository.deleteById(usageId);
    }

    @PostMapping("/{usageId}/steps")
    UsageStep newStep(@RequestBody UsageStep newStep, @PathVariable Long usageId){
        Optional<EquipmentUsage> usageOptional=usageRepository.findById(usageId);
        if(usageOptional.isPresent()){
            newStep.setEquipmentUsage(usageOptional.get());
            return usageStepRepository.save(newStep);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment Usage not found");
    }

    @GetMapping("/{usageId}/steps")
    public List<UsageStep> getAllSteps(@PathVariable Long usageId){
        Optional<EquipmentUsage> usageOptional=usageRepository.findById(usageId);
        if(usageOptional.isPresent()){
            return usageStepRepository.findByEquipmentUsageId(usageId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment Usage not found");
    }
}
