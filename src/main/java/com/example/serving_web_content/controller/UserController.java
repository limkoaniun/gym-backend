package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.User;
import com.example.serving_web_content.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    List<User> getAllPlayers() {
        return userRepository.findAll();
    }

    @PostMapping
    User createPlayer(@RequestBody User newplayer) {
        return userRepository.save(newplayer);
    }

    @GetMapping("/{userId}")
    User getPlayerByUserId(@PathVariable int userId) {
        Optional<User> optionalPlayer = userRepository.findById(userId);
        if (optionalPlayer.isPresent()) {
            return optionalPlayer.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    @PutMapping
    User replacePlayerByUserId(@RequestBody User newplayer) {
        Optional<User> optionalPlayer = userRepository.findById(newplayer.getUserId());
        if (optionalPlayer.isPresent()) {
            User user = optionalPlayer.get();
            user.setPassword(newplayer.getPassword());
            user.setEmail(newplayer.getEmail());
            return userRepository.save(user);
        }
        return userRepository.save(newplayer);
    }

    @DeleteMapping("/del/{userId}")
    void deletePlayer(@PathVariable int userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(userId);
    }

    @GetMapping("/search")
    List<User> searchPlayersByEmail(@RequestParam String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }


}
