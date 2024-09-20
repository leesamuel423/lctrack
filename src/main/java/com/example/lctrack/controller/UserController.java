package com.example.lctrack.controller;

import com.example.lctrack.model.User;
import com.example.lctrack.service.UserService;
import com.example.lctrack.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
    try {
      User savedUser = userService.registerUser(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/{username}")
  public User getUser(@PathVariable String username) {
    return userService.findByUsername(username);
  }
}
