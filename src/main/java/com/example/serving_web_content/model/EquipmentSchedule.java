package com.example.serving_web_content.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class EquipmentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Equipment equipment;

    private LocalDate scheduledDate;

    public EquipmentSchedule() {

    }

    public EquipmentSchedule(int id, User user, Equipment equipment, LocalDate scheduledDate) {
        this.id = id;
        this.user = user;
        this.equipment = equipment;
        this.scheduledDate = scheduledDate;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPlayer() {
        return user;
    }

    public void setPlayer(User user) {
        this.user = user;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}

