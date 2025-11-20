package com.example.serving_web_content.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class EquipmentUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    private Set<Muscle> muscles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Equipment equipment;

    @OneToMany(mappedBy = "equipmentUsage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsageStep> steps;

    public List<UsageStep> getSteps() {
        return steps;
    }

    public void setSteps(List<UsageStep> steps) {
        this.steps = steps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Muscle> getMuscles() {
        return muscles;
    }

    public void setMuscles(Set<Muscle> muscles) {
        this.muscles = muscles;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
