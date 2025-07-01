package com.example.serving_web_content.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class EquipmentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Equipment equipment;

    private LocalDate scheduledDate;

    public EquipmentSchedule() {

    }

    public EquipmentSchedule(int id, Player player, Equipment equipment, LocalDate scheduledDate) {
        this.id = id;
        this.player = player;
        this.equipment = equipment;
        this.scheduledDate = scheduledDate;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

