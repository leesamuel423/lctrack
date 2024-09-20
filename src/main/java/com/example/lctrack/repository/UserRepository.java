package com.example.lctrack.repository;

import com.example.lctrack.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
  User findByUsername(String username);
  User findByEmail(String email);
}
