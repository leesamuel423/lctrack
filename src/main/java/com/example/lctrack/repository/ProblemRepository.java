package com.example.lctrack.repository;

import com.example.lctrack.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String>{
  @Query(value = "{}", sort = "{ id : -1 }", fields = "{ id : 1 }")
  Problem findFirstByOrderByIdDesc();

  default int findHighestProblemNumber(){
    Problem problem = findFirstByOrderByIdDesc();
    return problem != null ? problem.getId() : 0;
  }

}
