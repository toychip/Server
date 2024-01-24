package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.converter.ProblemConverter;
import com.api.TaveShot.domain.compiler.domain.BojProblem;
import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.repository.ProblemRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemDto getProblemById(String id) {
        BojProblem bojProblem = problemRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorType._COMPILE_PROBLEM_NOT_FOUND));

        return ProblemConverter.convertToDto(bojProblem).orElseThrow(() -> new ApiException(ErrorType._PROBLEM_CONVERSION_ERROR));
    }
}