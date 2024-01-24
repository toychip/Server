package com.api.TaveShot.domain.compiler.repository;

import com.api.TaveShot.domain.compiler.domain.BojProblem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProblemRepository extends JpaRepository<BojProblem, String> {
}
