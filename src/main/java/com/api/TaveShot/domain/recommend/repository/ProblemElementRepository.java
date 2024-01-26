package com.api.TaveShot.domain.recommend.repository;

import com.api.TaveShot.domain.recommend.domain.ProblemElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemElementRepository extends JpaRepository<ProblemElement, Long> {

    @Query("select p from ProblemElement as p where p.problemId=:problemId")
    Optional<ProblemElement> findByProblemId(Long problemId);

}
