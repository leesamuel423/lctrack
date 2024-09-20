package com.example.lctrack.controller;

import com.example.lctrack.service.LeetCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/leetcode")
public class LeetCodeController {

  private final LeetCodeService leetCodeService;

  @Autowired
  public LeetCodeController(LeetCodeService leetCodeService) {
    this.leetCodeService = leetCodeService;
  }

  @PostMapping("/fetch")
  public Mono<String> fetchProblems() {
    return leetCodeService.fetchAndSaveProblems()
    .thenReturn("Problems fetched and saved successfully")
    .onErrorReturn("Error fetching problems");
  }
}
