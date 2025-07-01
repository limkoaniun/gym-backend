package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Player;
import com.example.serving_web_content.repository.PlayerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/login")
    public ResponseEntity<Player> login(@RequestBody Player loginRequest, HttpSession session) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<Player> userOpt = playerRepository.findByEmailAndPassword(email, password);

        if (userOpt.isPresent()) {
            Player user = userOpt.get();
            user.setPassword(null); // don't return password
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(5 * 60); // 5 minutes
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/currentUser")
    public Player getCurrentUser(HttpSession session) {
        Player currentUser = (Player) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in");
        }
        return currentUser;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Session invalidated.";
    }
}
