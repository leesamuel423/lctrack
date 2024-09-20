package com.example.lctrack.repository;

import com.example.lctrack.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, Integer> {
    Problem findFirstByOrderByIdDesc();
}
