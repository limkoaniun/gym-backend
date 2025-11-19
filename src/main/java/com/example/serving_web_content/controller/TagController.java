package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Equipment;
import com.example.serving_web_content.model.EquipmentTag;
import com.example.serving_web_content.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    List<EquipmentTag> getAll() {
        return tagRepository.findAll();
    }

    @PostMapping
    EquipmentTag newEquipmentTag(@RequestBody EquipmentTag newEquipmentTag) {
        return tagRepository.save(newEquipmentTag);
    }

    @DeleteMapping("/{equipmentTagId}")
    void deleteEquipmentTag(@PathVariable Long equipmentTagId){
        tagRepository.deleteById(equipmentTagId);
    }
}
