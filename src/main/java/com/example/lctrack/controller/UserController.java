package com.example.lctrack.controller;

import com.example.lctrack.model.User;
import com.example.lctrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public User registerUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @GetMapping("/{username}")
  public User getUser(@PathVariable String username) {
    return userService.findByUsername(username);
  }
}
