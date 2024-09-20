package com.example.lctrack.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String message) {
    // Pass message to parent RuntimeException class
    super(message);
  }
}
