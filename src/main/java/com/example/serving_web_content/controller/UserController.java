package com.example.serving_web_content.controller;

import com.example.serving_web_content.aop.RolesAllowed;
import com.example.serving_web_content.model.User;
import com.example.serving_web_content.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @RolesAllowed("admin")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    User createUser(@RequestBody User newUser) {
        newUser.setRole("customer");
        return userRepository.save(newUser);
    }

    @GetMapping("/{userId}")
    User getUserByUserId(HttpSession session, @PathVariable int userId) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (!currentUser.getRole().equals("admin") && currentUser.getId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Do not have permission to  update the user");
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    @PutMapping
    User updateUser(HttpSession session, @RequestBody User newUser, @PathVariable int id) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (!currentUser.getRole().equals("admin") && currentUser.getId() != id) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Do not have permission to  update the user");
        }

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User oldUser = userOptional.get();
            oldUser.setFirstName(newUser.getFirstName());
            oldUser.setLastName(newUser.getLastName());
            oldUser.setPassword(newUser.getPassword());
            userRepository.save(oldUser);
            return oldUser;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed("admin")
    void deleteUser(@PathVariable int userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(userId);
    }
}
