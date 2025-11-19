package com.example.serving_web_content.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UsageStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String instruction;
    private boolean isSetUp;
    @ManyToMany
    private List<Media> medias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_usage_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private EquipmentUsage equipmentUsage;

    public EquipmentUsage getEquipmentUsage() {
        return equipmentUsage;
    }

    public void setEquipmentUsage(EquipmentUsage equipmentUsage) {
        this.equipmentUsage = equipmentUsage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isSetUp() {
        return isSetUp;
    }

    public void setSetUp(boolean setUp) {
        isSetUp = setUp;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
