package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.converter.ProblemConverter;
import com.api.TaveShot.domain.compiler.domain.BojProblem;
import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.repository.ProblemRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemConverter problemConverter;

    @Autowired
    public ProblemService(ProblemRepository problemRepository, ProblemConverter problemConverter) {
        this.problemRepository = problemRepository;
        this.problemConverter = problemConverter;
    }

    public ProblemDto getProblemById(String id) {
        BojProblem bojProblem = problemRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorType._PROBLEM_NOT_FOUND));

        return problemConverter.convertToDto(bojProblem);
    }
}