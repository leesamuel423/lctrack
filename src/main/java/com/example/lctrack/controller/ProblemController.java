package com.example.lctrack.controller;

import com.example.lctrack.model.Problem;
import com.example.lctrack.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
  @Autowired
  private ProblemService problemService;

  @GetMapping("/")
  public List<Problem> getAllProblems() {
    return problemService.getAllProblems();
  }

  @GetMapping("/{id}")
  public Problem getProblemById(@PathVariable String id) {
    return problemService.getProblemById(id);
  }
}
