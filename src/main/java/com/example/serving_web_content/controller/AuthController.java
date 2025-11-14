package com.example.serving_web_content.controller;

import com.example.serving_web_content.dto.GoogleLoginRequest;
import com.example.serving_web_content.model.User;
import com.example.serving_web_content.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Value("${google.client-id}")
    private String googleClientId;

    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @PostConstruct
    public void init() throws Exception {
        this.googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance()).setAudience(Collections.singletonList(googleClientId)).build();
    }


    @PostMapping("/google")
    public ResponseEntity<User> googleLogin(@RequestBody GoogleLoginRequest request, HttpSession session) {
        String idTokenString = request.getIdToken();
        if (idTokenString == null || idTokenString.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);
            if (idToken == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            Optional<User> userOpt = userRepository.findByEmail(email);
            User user;
            if (userOpt.isPresent()) {
                user = userOpt.get();
            } else {
                user = new User();
                user.setEmail(email);
                user.setUsername(email);
                user.setPassword(null);
                user.setFullName(name);
                user.setRole("USER");
                user = userRepository.save(user);
            }

            user.setPassword(null);
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(5 * 60);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // malformed token, for example copied with &authuser=...
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest, HttpSession session) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> userOpt = userRepository.findByEmailAndPassword(email, password);
        // Allow use both email and username to login
        if (!userOpt.isPresent()) {
            userOpt = userRepository.findByUsernameAndPassword(email, password);
        }
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
