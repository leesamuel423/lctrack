package com.example.lctrack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Represents a user in the application.
 */
@Document(collection = "users")
public class User {

  @Id
  private String id;

  @NotBlank(message = "Username is required")
  @Size(min = 8, max = 20)
  private String username;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @Email(message = "Email should be valid")
  private String email;

  private String phoneNumber;

  private List<UserProblemProgress> problemsList; // User's progress on problems

  // Constructors

  public User() {}

  public User(String username, String password, String email, String phoneNumber, List<UserProblemProgress> problemsList) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.problemsList = problemsList;
  }

  // Getters and Setters

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<UserProblemProgress> getProblemsList() {
    return problemsList;
  }

  public void setProblemsList(List<UserProblemProgress> problemsList) {
    this.problemsList = problemsList;
  }

}
