package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Player;
import com.example.serving_web_content.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @PostMapping
    Player createPlayer(@RequestBody Player newplayer) {
        return playerRepository.save(newplayer);
    }

    @GetMapping("/{userId}")
    Player getPlayerByUserId(@PathVariable int userId) {
        Optional<Player> optionalPlayer = playerRepository.findById(userId);
        if (optionalPlayer.isPresent()) {
            return optionalPlayer.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
    }


    @PutMapping
    Player replacePlayerByUserId(@RequestBody Player newplayer) {
        Optional<Player> optionalPlayer = playerRepository.findById(newplayer.getUserId());
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setPassword(newplayer.getPassword());
            player.setEmail(newplayer.getEmail());
            return playerRepository.save(player);
        }
        return playerRepository.save(newplayer);
    }

    @DeleteMapping("/del/{userId}")
    void deletePlayer(@PathVariable int userId) {
        if (!playerRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        playerRepository.deleteById(userId);
    }

    @GetMapping("/search")
    List<Player> searchPlayersByEmail(@RequestParam String email) {
        return playerRepository.findByEmailContainingIgnoreCase(email);
    }


}
