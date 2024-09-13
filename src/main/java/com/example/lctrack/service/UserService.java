package com.example.lctrack.service;

import com.example.lctrack.model.User;
import com.example.lctrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        // Hash the password before saving
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    private String hashPassword(String plainPassword) {
        // Use BCrypt or another hashing algorithm
        // Example with BCrypt (you'll need to add the dependency)
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
