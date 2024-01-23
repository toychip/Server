package com.api.TaveShot.domain.compiler.repository;

import com.api.TaveShot.domain.compiler.domain.BojProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<BojProblem, String> {
}
