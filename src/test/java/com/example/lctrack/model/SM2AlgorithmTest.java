package com.example.lctrack.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SM2AlgorithmTest {

  @Test
  void testCalculateWithQualityLessThan3() {
    SM2Algorithm.SM2Result result = SM2Algorithm.calculate(2, 5, 2.5, 10);
    assertEquals(0, result.repetition);
    assertEquals(1, result.interval);
    assertTrue(result.easinessFactor < 2.5);
    assertEquals(LocalDate.now().plusDays(1), result.nextReviewDate);
  }

  @Test
  void testCalculateWithQualityGreaterThan3() {
    SM2Algorithm.SM2Result result = SM2Algorithm.calculate(4, 1, 2.5, 1);
    assertEquals(2, result.repetition);
    assertEquals(6, result.interval);
    assertTrue(result.easinessFactor >= 2.5);
    assertEquals(LocalDate.now().plusDays(6), result.nextReviewDate);
  }

  @Test
  void testCalculateWithMaxQuality() {
    SM2Algorithm.SM2Result result = SM2Algorithm.calculate(5, 3, 2.5, 10);
    assertEquals(4, result.repetition);
    assertTrue(result.interval > 10);
    assertTrue(result.easinessFactor > 2.5);
    assertTrue(result.nextReviewDate.isAfter(LocalDate.now().plusDays(10)));
  }

  @Test
  void testCalculateWithInvalidQuality() {
    assertThrows(IllegalArgumentException.class, () -> SM2Algorithm.calculate(6, 1, 2.5, 1));
  }
}
