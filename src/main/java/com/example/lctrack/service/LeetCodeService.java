package com.example.lctrack.service;

import com.example.lctrack.model.Problem;
import com.example.lctrack.model.enums.Difficulty;
import com.example.lctrack.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class LeetCodeService {

  private final WebClient webClient;
  private final ProblemRepository problemRepository;

  @Autowired
  public LeetCodeService(ProblemRepository problemRepository) {
    // Init webclient w/ base URL and headers
    this.webClient = WebClient.builder()
    .baseUrl("https://leetcode.com")
    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    .defaultHeader("Referer", "https://leetcode.com")
    .build();
    this.problemRepository = problemRepository;
  }

  // Main method to fetch and save problems
  public Mono<Void> fetchAndSaveProblems() {
    // Get highest problem ID from database
    int highestId = getHighestProblemId();
    // Fetch problems starting from highestId, then save new ones
    return fetchProblems(highestId)
    .flatMap(this::saveNewProblems)
    .onErrorResume(e -> {
      System.err.println("Error fetching problems: " + e);
      return Mono.empty();
    });
  }

  // Helper method to get highest problem ID from db
  private int getHighestProblemId() {
    Problem highestProblem = problemRepository.findFirstByOrderByIdDesc();
    return highestProblem != null ? highestProblem.getId() : 0;
  }

  // Method to fetch problems from LeetCode graphql API
  private Mono<Map<String, Object>> fetchProblems(int skip) {
    // GraphQL query string
    String query = "query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) {" +
    "problemsetQuestionList: questionList(categorySlug: $categorySlug, limit: $limit, skip: $skip, filters: $filters) {" +
    "total: totalNum questions: data {" +
    "frontendQuestionId: questionFrontendId title difficulty" +
    "}}}\n";

    // Variables for query
    Map<String, Object> variables = Map.of(
      "categorySlug", "",
      "skip", skip,
      "limit", 100,
      "filters", Map.of()
    );

    // Request body combining query and variables
    Map<String, Object> requestBody = Map.of(
      "query", query,
      "variables", variables
    );

    // Make POST request to API
    return webClient.post()
    .uri("/graphql")
    .bodyValue(requestBody)
    .retrieve()
    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>(){});
  }

  // Method to save new problems to db
  private Mono<Void> saveNewProblems(Map<String, Object> response) {
    if (response == null || !response.containsKey("data")) {
      return Mono.error(new RuntimeException("Invalid response format"));
    }

    Object dataObj = response.get("data");

    if (!(dataObj instanceof Map)) {
      return Mono.error(new RuntimeException("Invalid data format"));
    }

    @SuppressWarnings("unchecked")
    Map<String, Object> data = (Map<String, Object>) dataObj;

    Object problemsetQuestionListObj = data.get("problemsetQuestionList");
    if (!(problemsetQuestionListObj instanceof Map)) {
      return Mono.error(new RuntimeException("Invalid problemsetQuestionList format"));
    }

    @SuppressWarnings("unchecked")
    Map<String, Object> problemsetQuestionList = (Map<String, Object>) problemsetQuestionListObj;

    Object questionsObj = problemsetQuestionList.get("questions");
    if (!(questionsObj instanceof List)) {
      return Mono.error(new RuntimeException("Invalid questions format"));
    }

    @SuppressWarnings("unchecked")
    List<Map<String, Object>> questions = (List<Map<String, Object>>) questionsObj;

    // Iterate through questions and save new ones 
    for (Map<String, Object> questionData : questions) {
      int id = Integer.parseInt((String) questionData.get("frontendQuestionId"));
      if (!problemRepository.existsById(id)) {
        // Create and save a new Problem entity
        Problem problem = new Problem();
        problem.setId(id);
        problem.setName((String) questionData.get("title"));
        problem.setDifficulty(Difficulty.valueOf(((String) questionData.get("difficulty")).toUpperCase()));
        problemRepository.save(problem);
        System.out.println("Saved new problem: " + problem.getId() + ". " + problem.getName());
      }
    }

    return Mono.empty();
  }

  // Scheduled method to run fetchAndSaveProblems every Sunday at midnight
  @Scheduled(cron = "0 0 0 * * SUN")
  public void scheduledFetchAndSaveProblems() {
    fetchAndSaveProblems().subscribe();
  }
}
