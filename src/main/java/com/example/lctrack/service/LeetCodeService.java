package com.example.lctrack.service;

import com.example.lctrack.model.Problem;
import com.example.lctrack.model.enums.Difficulty;
import com.example.lctrack.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class LeetCodeService {

  private final WebClient webClient;
  private final ProblemRepository problemRepository;

  @Autowired
  public LeetCodeService(ProblemRepository problemRepository) {
    this.webClient = WebClient.create("https://leetcode.com");
    this.problemRepository = problemRepository;
  }

  public Mono<Void> fetchAndSaveProblems() {
    String query = "query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) { " +
    "problemsetQuestionList: questionList( categorySlug: $categorySlug, limit: $limit, skip: $skip, filters: $filters ) { " +
    "total: totalNum, questions: data { difficulty, frontendQuestionId: questionFrontendId, title } } }";

    Map<String, Object> initialVariables = Map.of(
      "categorySlug", "",
      "skip", 0,
      "limit", 1,
      "filters", Map.of()
    );

    Map<String, Object> initialRequestBody = Map.of(
      "query", query,
      "variables", initialVariables
    );

    return webClient.post()
    .uri("/graphql")
    .bodyValue(initialRequestBody)
    .retrieve()
    .bodyToMono(Map.class)
    .flatMapMany(response -> {
      Map<String, Object> data = (Map<String, Object>) response.get("data");
      if (data == null) {
        return Mono.error(new RuntimeException("Response data is null"));
      }

      Map<String, Object> problemsetQuestionList = (Map<String, Object>) data.get("problemsetQuestionList");
      if (problemsetQuestionList == null) {
        return Mono.error(new RuntimeException("Problem set question list is null"));
      }

      Integer total = (Integer) problemsetQuestionList.get("total");
      if (total == null) {
        return Mono.error(new RuntimeException("Total number of problems is null"));
      }

      // Set maximum limit per batch to avoid exceeding API limitations
      int MAX_LIMIT = 1000;
      int batchCount = (total + MAX_LIMIT - 1) / MAX_LIMIT;

      // Create a Flux to process each batch sequentially
      return Flux.range(0, batchCount)
      .concatMap(batchIndex -> {
        int skip = batchIndex * MAX_LIMIT;
        int limit = Math.min(MAX_LIMIT, total - skip);

        Map<String, Object> batchVariables = Map.of(
          "categorySlug", "",
          "skip", skip,
          "limit", limit,
          "filters", Map.of()
        );

        Map<String, Object> batchRequestBody = Map.of(
          "query", query,
          "variables", batchVariables
        );

        return webClient.post()
        .uri("/graphql")
        .bodyValue(batchRequestBody)
        .retrieve()
        .bodyToMono(Map.class)
        .flatMap(this::processResponse);
      });
    })
    .then()
    .onErrorResume(e -> {
      e.printStackTrace();
      return Mono.empty();
    });
  }

  private Mono<Void> processResponse(Map<String, Object> response) {
    Map<String, Object> data = (Map<String, Object>) response.get("data");
    if (data == null) {
      return Mono.error(new RuntimeException("Response data is null"));
    }

    Map<String, Object> problemsetQuestionList = (Map<String, Object>) data.get("problemsetQuestionList");
    if (problemsetQuestionList == null) {
      return Mono.error(new RuntimeException("Problem set question list is null"));
    }

    List<Map<String, Object>> questions = (List<Map<String, Object>>) problemsetQuestionList.get("questions");
    if (questions == null) {
      return Mono.error(new RuntimeException("Questions list is null"));
    }

    for (Map<String, Object> questionData : questions) {
      try {
        Problem problem = new Problem();

        // Parse and set ID
        String idStr = (String) questionData.get("frontendQuestionId");
        int id = Integer.parseInt(idStr);
        problem.setId(id);

        // Check if the problem already exists in the database
        if (problemRepository.existsById(id)) {
          continue; // Skip existing problem
        }

        // Set name
        problem.setName((String) questionData.get("title"));

        // Parse and set difficulty
        String difficultyStr = (String) questionData.get("difficulty");
        Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
        problem.setDifficulty(difficulty);

        // Save problem
        problemRepository.save(problem);

      } catch (Exception e) {
        // Handle parsing or saving exceptions
        e.printStackTrace();
        continue;
      }
    }

    return Mono.empty();
  }

  @Scheduled(cron = "0 0 0 * * SUN") // Run at midnight every Sunday
  public void scheduledFetchAndSaveProblems() {
    fetchAndSaveProblems().subscribe();
  }
}
