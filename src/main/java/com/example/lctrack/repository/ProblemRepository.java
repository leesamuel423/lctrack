package com.example.lctrack.repository;

import com.example.lctrack.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface ProblemRepository extends MongoRepository<Problem, Integer> {
  Problem findFirstByOrderByIdDesc();
  Problem findById();

  @Query("{'nextReviewDate': {$lte: ?0}}")
  List<Problem> findProblemsDueForReview(LocalDate date);

}
