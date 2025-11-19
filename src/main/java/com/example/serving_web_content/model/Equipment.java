package com.example.serving_web_content.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    private Set<EquipmentTag> tags;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipmentUsage> usages;

    @ManyToMany
    private List<Media> medias;
    private String description;

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

    public Set<EquipmentTag> getTags() {
        return tags;
    }

    public void setTags(Set<EquipmentTag> tags) {
        this.tags = tags;
    }

    public List<EquipmentUsage> getUsages() {
        return usages;
    }

    public void setUsages(List<EquipmentUsage> usages) {
        this.usages = usages;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}