package com.example.serving_web_content.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;

    @ManyToMany
    private List<Equipment> favouredEquipments;

    public List<Equipment> getFavouredEquipments() {
        return favouredEquipments;
    }

    public void setFavouredEquipments(List<Equipment> favouredEquipments) {
        this.favouredEquipments = favouredEquipments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        if (firstName == null && lastName == null) return null;
        if (lastName == null || lastName.isEmpty()) return firstName;
        return firstName + " " + lastName;
    }

    public void setFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.firstName = null;
            this.lastName = null;
            return;
        }

        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            this.firstName = parts[0];
            this.lastName = "";
        } else {
            this.firstName = parts[0];
            this.lastName = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
