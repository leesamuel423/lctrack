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

  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  public Problem getProblemById(String id) {
    try{
      Integer intId = Integer.parseInt(id);
      return problemRepository.findById(intId).orElse(null);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public Problem saveProblem(Problem problem) {
    return problemRepository.save(problem);
  }

  // Method to bulk save all Leetcode problems
  public void saveAllProblems(List<Problem> problems) {
    problemRepository.saveAll(problems);
  }
}
