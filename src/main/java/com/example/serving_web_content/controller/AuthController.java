package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.User;
import com.example.serving_web_content.repository.UserRepository;
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
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest, HttpSession session) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> userOpt = userRepository.findByEmailAndPassword(email, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(null); // don't return password
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(5 * 60); // 5 minutes
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/currentUser")
    public User getCurrentUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
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
