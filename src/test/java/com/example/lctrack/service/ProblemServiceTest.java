package com.example.lctrack.service;

import com.example.lctrack.model.Problem;
import com.example.lctrack.repository.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProblemServiceTest {

  @Mock
  private ProblemRepository problemRepository;

  @InjectMocks
  private ProblemService problemService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testUpdateProblemWithSM2() {
    Problem problem = new Problem();
    problem.setId("1");
    problem.setRepetition(1);
    problem.setEasinessFactor(2.5);
    problem.setInterval(1);

    when(problemRepository.findById("1")).thenReturn(Optional.of(problem));
    when(problemRepository.save(any(Problem.class))).thenReturn(problem);

    Problem updatedProblem = problemService.updateProblemWithSM2("1", 4);


    verify(problemRepository, times(1)).findById("1");
    verify(problemRepository, times(1)).save(problem);
    assertNotNull(updatedProblem);
    assertEquals(2, updatedProblem.getRepetition());
    assertEquals(6, updatedProblem.getInterval());
    assertTrue(updatedProblem.getEasinessFactor() >= 2.5);
    assertNotNull(updatedProblem.getNextReviewDate());

    verify(problemRepository, times(1)).findById("1");
    verify(problemRepository, times(1)).save(problem);
  }

  @Test
  void testUpdateProblemWithSM2_ProblemNotFound() {
    when(problemRepository.findById("1")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> problemService.updateProblemWithSM2("1", 4));

    verify(problemRepository, times(1)).findById("1");
    verify(problemRepository, never()).save(any(Problem.class));
  }
}
