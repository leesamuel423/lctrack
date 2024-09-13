package com.example.lctrack.model;

import java.time.LocalDate;

/**
* Represents user's progress on a specific problem
*/
public class UserProblemProgress {
  private int problemId; // Referencing Problem's ID
  private LocalDate lastSolved;
  private LocalDate nextSolve;
  private int totalSolves;
  private double multiplier;

  // Constructors

  public UserProblemProgress(){}

  public UserProblemProgress(int problemId, LocalDate lastSolved, LocalDate nextSolve, int totalSolves, double multiplier){
    this.problemId = problemId;
    this.lastSolved = lastSolved;
    this.nextSolve = nextSolve;
    this.totalSolves = totalSolves;
    this.multiplier = multiplier;
  }

  // Getters and Setters

  public int getProblemId() {
    return problemId;
  }

  public void setProblemId(int problemId) {
    this.problemId= problemId;
  }

  public LocalDate getLastSolved() {
    return lastSolved;
  }

  public void setLastSolved(LocalDate lastSolved) {
    this.lastSolved= lastSolved;
  }

  public LocalDate getNextSolve() {
    return nextSolve;
  }

  public void setNextSolve(LocalDate nextSolve) {
    this.nextSolve= nextSolve;
  }

  public int getTotalSolves() {
    return totalSolves;
  }

  public void setTotalSolves(int totalSolves) {
    this.totalSolves= totalSolves;
  }

  public double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(double multiplier) {
    this.multiplier= multiplier;
  }
}

/** Notes
* Lazy Loading -> When you need problem details, fetch it from problem collection using the problemId
*/
