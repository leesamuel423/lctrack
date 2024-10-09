package com.example.lctrack.service;

import com.example.lctrack.model.Problem;
import com.example.lctrack.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class ProblemService {

  private final ProblemRepository problemRepository;

  @Autowired
  public ProblemService(ProblemRepository problemRepository) {
    this.problemRepository = problemRepository;
  }

  // Method to retrieve all problems from database
  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  // Method to retrieve single problem by ID
  public Problem getProblemById(String id) {
    return problemRepository.findById(id).orElse(null);
  }

  // Method to save a new problem or update an existing one
  public Problem saveProblem(Problem problem) {
    return problemRepository.save(problem);
  }

  // Method to apply SM2 algorithm
  public Problem updateProblemWithSM2(String id, int quality) {
    Problem problem = problemRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Problem not found"));
    problem.applySM2(quality);
    return problemRepository.save(problem);
  }

  // Method to get problems due for review
  public List<Problem> getProblemsDueForReview() {
    return problemRepository.findProblemsDueForReview(LocalDate.now());
  }
}
