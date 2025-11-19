package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Equipment;
import com.example.serving_web_content.model.Muscle;
import com.example.serving_web_content.repository.EquipmentRepository;
import com.example.serving_web_content.repository.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/muscles")
public class MuscleController {
    @Autowired
    private MuscleRepository muscleRepository;

    @GetMapping()
    List<Muscle> getAll(){
        return muscleRepository.findAll();
    }

    @PostMapping
    Muscle newMuscle(@RequestBody Muscle newMuscle){
        return muscleRepository.save(newMuscle);
    }

    @DeleteMapping("/{muscleId}")
    void deleteMuscle(@PathVariable Long muscleId){
        muscleRepository.deleteById(muscleId);
    }
}
