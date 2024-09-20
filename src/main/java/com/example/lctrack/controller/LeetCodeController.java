package com.example.lctrack.controller;

import com.example.lctrack.service.LeetCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leetcode")
public class LeetCodeController {

  private final LeetCodeService leetCodeService;

  @Autowired
  public LeetCodeController(LeetCodeService leetCodeService) {
    this.leetCodeService = leetCodeService;
  }

  @PostMapping("/fetch")
  public ResponseEntity<String> fetchProblems() {
    leetCodeService.fetchAndSaveProblems().subscribe();
    return ResponseEntity.ok("Fetch operation started");
  }
}
