package com.example.lctrack.service;

import com.example.lctrack.model.Problem;
import com.example.lctrack.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProblemService {

  @Autowired
  private ProblemRepository problemRepository;

  public ProblemService(ProblemRepository problemRepository) {
    this.problemRepository = problemRepository;
  }

  // Method to retrieve all probs from database
  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  // Method to retrieve single prob by ID
  public Problem getProblemById(String id) {
    try {
      Integer intId = Integer.parseInt(id);
      return problemRepository.findById(intId).orElse(null);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  // Method to save a new problem or update an existing one
  public Problem saveProblem(Problem problem) {
    return problemRepository.save(problem);
  }

}
