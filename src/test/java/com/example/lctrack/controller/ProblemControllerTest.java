package com.example.lctrack.controller;

import com.example.lctrack.model.Problem;
import com.example.lctrack.service.ProblemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProblemControllerTest {

  @Mock
  private ProblemService problemService;

  @InjectMocks
  private ProblemController problemController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testReviewProblem() {
    Problem problem = new Problem();
    problem.setId("1");
    problem.setRepetition(2);
    problem.setEasinessFactor(2.6);
    problem.setInterval(6);

    when(problemService.updateProblemWithSM2("1", 4)).thenReturn(problem);

    ResponseEntity<Problem> response = problemController.reviewProblem("1", 4);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(problem, response.getBody());

    verify(problemService, times(1)).updateProblemWithSM2("1", 4);
  }

  @Test
  void testReviewProblem_ProblemNotFound() {
    when(problemService.updateProblemWithSM2("1", 4)).thenThrow(new RuntimeException("Problem not found"));

    assertThrows(RuntimeException.class, () -> problemController.reviewProblem("1", 4));

    verify(problemService, times(1)).updateProblemWithSM2("1", 4);
  }
}
