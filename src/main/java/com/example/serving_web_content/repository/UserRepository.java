package com.example.serving_web_content.repository;

import com.example.serving_web_content.model.Equipment;
import com.example.serving_web_content.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);
    // this is like 'select count(*) > 0 from player where username = ...'

    List<User> findByEmailContainingIgnoreCase(String email);

    User findByUsername(String username);

    Optional<User> findByEmail(String Email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("Select u.favouredEquipments From User u Where u.id = :userId")
    List<Equipment> findFavouredByUser(@Param("userId") Long userId);
}