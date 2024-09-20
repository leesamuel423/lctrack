package com.example.lctrack.service;

import com.example.lctrack.exception.UserAlreadyExistsException;
import com.example.lctrack.model.User;
import com.example.lctrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User saveUser(User user) {
    // Hash the password before saving
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User registerUser(User user) throws UserAlreadyExistsException {
    // Check if username or email already exists
    if (userRepository.findByUsername(user.getUsername()) != null) {
      throw new UserAlreadyExistsException("Username already exists");
    }

    if (userRepository.findByEmail(user.getEmail()) != null) {
      throw new UserAlreadyExistsException("Email already registered");
    }

    // Hash the password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Save the user
    return userRepository.save(user);
  }

}
