package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.UsageStep;
import com.example.serving_web_content.repository.UsageStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/steps")
public class UsageStepController {
    @Autowired
    private UsageStepRepository stepRepository;

    @PutMapping("/{stepId}")
    UsageStep replaceStep(@RequestBody UsageStep newStep, @PathVariable Long stepId){
        Optional<UsageStep> stepOptional=stepRepository.findById(stepId);
        if(stepOptional.isPresent()){
            UsageStep oldStep=stepOptional.get();
            oldStep.setEquipmentUsage(newStep.getEquipmentUsage());
            oldStep.setInstruction(newStep.getInstruction());
            oldStep.setMedias(newStep.getMedias());
            oldStep.setSetUp(newStep.isSetUp());
            oldStep.setTitle(newStep.getTitle());
            stepRepository.save(oldStep);
            return oldStep;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usage Step not found");
    }

    @DeleteMapping("/{stepId}")
    void deleteStep(@PathVariable Long stepId){
        stepRepository.deleteById(stepId);
    }
}
