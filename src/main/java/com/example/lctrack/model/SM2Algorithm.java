package com.example.lctrack.model;

import java.time.LocalDate;

public class SM2Algorithm {
  private static final double MIN_EASINESS_FACTOR = 1.3;

  public static class SM2Result {
    public int repetition;
    public double easinessFactor;
    public int interval;
    public LocalDate nextReviewDate;

    public SM2Result(int repetition, double easinessFactor, int interval, LocalDate nextReviewDate) {
      this.repetition = repetition;
      this.easinessFactor = easinessFactor;
      this.interval = interval;
      this.nextReviewDate = nextReviewDate;
    }
  }

  public static SM2Result calculate(int quality, int repetition, double easinessFactor, int interval) {
    if (quality < 0 || quality > 5) {
      throw new IllegalArgumentException("Quality must be between 0 and 5");
    }

    // Calculate new easiness factor
    double newEasinessFactor = easinessFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
    newEasinessFactor = Math.max(MIN_EASINESS_FACTOR, newEasinessFactor);

    int newRepetition, newInterval;

    if (quality < 3) {
      newRepetition = 0;
      newInterval = 1;
    } else {
      newRepetition = repetition + 1;
      if (newRepetition == 1) {
        newInterval = 1;
      } else if (newRepetition == 2) {
        newInterval = 6;
      } else {
        newInterval = (int) Math.round(interval * newEasinessFactor);
      }
    }

    LocalDate nextReviewDate = LocalDate.now().plusDays(newInterval);

    return new SM2Result(newRepetition, newEasinessFactor, newInterval, nextReviewDate);
  }
}
