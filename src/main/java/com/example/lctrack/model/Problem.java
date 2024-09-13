package com.example.lctrack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.lctrack.model.enums.Difficulty;

/**
* Represents a Leetcode Problem
*/
@Document(collection = "problems")
public class Problem {

  @Id
  private int id;
  private String name;
  private Difficulty difficulty;
  private String url;

  // Constructors

  public Problem(){}

  public Problem(int id, String name, Difficulty difficulty, String url) {
    this.id = id;
    this.name = name;
    this.difficulty = difficulty;
    this.url = url;
  }

  // Getters and Setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
