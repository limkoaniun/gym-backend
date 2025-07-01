package com.example.serving_web_content.repository;

import com.example.serving_web_content.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByUsernameAndPassword(String username, String password);
    // this works exactly like 'select * from player where username = ... and password = ...'

    boolean existsByUsername(String username);
    // this is like 'select count(*) > 0 from player where username = ...'

    List<Player> findByEmailContainingIgnoreCase(String email);

    Player findByUsername(String username);

    Optional<Player> findByEmailAndPassword(String email, String password);
}