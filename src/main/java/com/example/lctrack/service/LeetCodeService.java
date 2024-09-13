package com.example.lctrack.service;

import com.example.lctrack.model.Problem;
import com.example.lctrack.model.enums.Difficulty;
import com.example.lctrack.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

  public void fetchAndSaveProblems() {
    int highestProblemNumber = problemRepository.findHighestProblemNumber();
    int skip = highestProblemNumber;

    String query = "query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) { " +
    "problemsetQuestionList: questionList( categorySlug: $categorySlug limit: $limit skip: $skip filters: $filters ) { " +
    "total: totalNum questions: data { difficulty frontendQuestionId: questionFrontendId title } } }";

    Map<String, Object> variables = Map.of(
      "categorySlug", "",
      "skip", skip,
      "limit", 50,
      "filters", Map.of()
    );

    Map<String, Object> requestBody = Map.of(
      "query", query,
      "variables", variables
    );

    webClient.post()
      .uri("/graphql")
      .bodyValue(requestBody)
      .retrieve()
      .bodyToMono(Map.class)
      .flatMap(this::processResponse)
      .block();
  }

  private Mono<Void> processResponse(Map<String, Object> response) {
    Map<String, Object> data = (Map<String, Object>) response.get("data");
    Map<String, Object> problemsetQuestionList = (Map<String, Object>) data.get("problemsetQuestionList");
    List<Map<String, Object>> questions = (List<Map<String, Object>>) problemsetQuestionList.get("questions");

    for (Map<String, Object> questionData : questions) {
      Problem problem = new Problem();
      problem.setId((int) questionData.get("frontendQuestionId"));
      problem.setName((String) questionData.get("title"));
      problem.setDifficulty((Difficulty) questionData.get("difficulty"));
      problemRepository.save(problem);
    }

    return Mono.empty();
  }

  @Scheduled(cron = "0 0 0 * * SUN") // Run midnight every sunday
  public void scheduledFetchAndSaveProblems() {
    fetchAndSaveProblems();
  }
}
