package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Equipment;
import com.example.serving_web_content.model.EquipmentUsage;
import com.example.serving_web_content.repository.EquipmentRepository;
import com.example.serving_web_content.repository.EquipmentUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentUsageRepository equipmentUsageRepository;

    @GetMapping()
    List<Equipment> getAll(){
        return equipmentRepository.findAll();
    }

    @GetMapping("/{equipmentId}")
    Equipment getById(@PathVariable Long equipmentId){
        Optional<Equipment> result = equipmentRepository.findById(equipmentId);
        return result.orElse(null);
    }

    @PostMapping
    Equipment newEquipment(@RequestBody Equipment newEquipment){
        return equipmentRepository.save(newEquipment);
    }

    @PutMapping("/{equipmentId}")
    Equipment replaceEquipment (@RequestBody Equipment newEquipment, @PathVariable Long equipmentId){
        Optional<Equipment> equipmentOptional=equipmentRepository.findById(equipmentId);
        if(equipmentOptional.isPresent()){
            Equipment oldequipment=equipmentOptional.get();
            oldequipment.setName(newEquipment.getName());
            oldequipment.setTags(newEquipment.getTags());
            oldequipment.setUsages(newEquipment.getUsages());
            oldequipment.setMedias(newEquipment.getMedias());
            oldequipment.setDescription(newEquipment.getDescription());
            equipmentRepository.save(oldequipment);
            return oldequipment;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found");
    }

    @DeleteMapping("/{equipmentId}")
    void deleteEquipment(@PathVariable Long equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }

    @PostMapping("/{equipmentId}/usages")
    EquipmentUsage newUsage(@RequestBody EquipmentUsage newUsage, @PathVariable Long equipmentId){
        Optional<Equipment> equipmentOptional=equipmentRepository.findById(equipmentId);
        if(equipmentOptional.isPresent()){
            newUsage.setEquipment(equipmentOptional.get());
            return equipmentUsageRepository.save(newUsage);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found");
    }

    @GetMapping("/{equipmentId}/usages")
    public List<EquipmentUsage> getAllUsages(@PathVariable Long equipmentId) {

        Optional<Equipment> equipmentOptional = equipmentRepository.findById(equipmentId);
        if (equipmentOptional.isPresent()) {
            return equipmentUsageRepository.findByEquipmentId(equipmentId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found");
    }
}
