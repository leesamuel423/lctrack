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
    this.webClient = WebClient.builder()
    .baseUrl("https://leetcode.com")
    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    .defaultHeader("Referer", "https://leetcode.com")
    .build();
    this.problemRepository = problemRepository;
  }

  public Mono<Void> fetchAndSaveProblems() {
    int highestId = getHighestProblemId();
    return fetchProblems(highestId)
    .flatMap(this::saveNewProblems)
    .onErrorResume(e -> {
      System.err.println("Error fetching problems: " + e);
      return Mono.empty();
    });
  }

  private int getHighestProblemId() {
    Problem highestProblem = problemRepository.findFirstByOrderByIdDesc();
    return highestProblem != null ? highestProblem.getId() : 0;
  }

  private Mono<Map<String, Object>> fetchProblems(int skip) {
    String query = "query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) {" +
    "problemsetQuestionList: questionList(categorySlug: $categorySlug, limit: $limit, skip: $skip, filters: $filters) {" +
    "total: totalNum questions: data {" +
    "frontendQuestionId: questionFrontendId title difficulty" +
    "}}}\n";

    Map<String, Object> variables = Map.of(
      "categorySlug", "",
      "skip", skip,
      "limit", 100,
      "filters", Map.of()
    );

    Map<String, Object> requestBody = Map.of(
      "query", query,
      "variables", variables
    );

    return webClient.post()
    .uri("/graphql")
    .bodyValue(requestBody)
    .retrieve()
    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>(){});
  }

  private Mono<Void> saveNewProblems(Map<String, Object> response) {
    Map<String, Object> data = (Map<String, Object>) response.get("data");
    Map<String, Object> problemsetQuestionList = (Map<String, Object>) data.get("problemsetQuestionList");
    List<Map<String, Object>> questions = (List<Map<String, Object>>) problemsetQuestionList.get("questions");

    for (Map<String, Object> questionData : questions) {
      int id = Integer.parseInt((String) questionData.get("frontendQuestionId"));
      if (!problemRepository.existsById(id)) {
        Problem problem = new Problem();
        problem.setId(id);
        problem.setName((String) questionData.get("title"));
        problem.setDifficulty(Difficulty.valueOf(((String) questionData.get("difficulty")).toUpperCase()));
        problemRepository.save(problem);
        System.out.println("Saved new problem: " + problem.getName());
      }
    }

    return Mono.empty();
  }

  @Scheduled(cron = "0 0 0 * * SUN") // Run at midnight every Sunday
  public void scheduledFetchAndSaveProblems() {
    fetchAndSaveProblems().subscribe();
  }
}
