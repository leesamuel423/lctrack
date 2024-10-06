package com.example.lctrack.controller;

import com.example.lctrack.model.Problem;
import com.example.lctrack.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
  @Autowired
  private ProblemService problemService;

  // Endpoint to get all problems, mapped to GET /api/problems/
  @GetMapping("/")
  public List<Problem> getAllProblems() {
    return problemService.getAllProblems();
  }

  // Endpoint to get a problem by ID, mapped to GET /api/problems/{id}
  @GetMapping("/{id}")
  public Problem getProblemById(@PathVariable String id) {
    return problemService.getProblemById(id);
  }

  // Endpoint for SM2 updates
  @PostMapping("/{id}/review")
  public ResponseEntity<Problem> reviewProblem(@PathVariable String id, @RequestParam int quality) {
    int idInteger = Integer.parseInt(id);
    Problem updatedProblem = problemService.updateProblemWithSM2(idInteger, quality);
    return ResponseEntity.ok(updatedProblem);
  }

  // Endpoint for getting problems due for review
  @GetMapping("/due-for-review")
  public ResponseEntity<List<Problem>> getProblemsDueForReview() {
    List<Problem> problems = problemService.getProblemsDueForReview();
    return ResponseEntity.ok(problems);
  }
}
