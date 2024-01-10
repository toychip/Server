package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.CodeSubmissionDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionResultDto;
import com.api.TaveShot.domain.compiler.service.CompilerService;
import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.service.ProblemService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompilerController {

    private final CompilerService compilerService;
    private final ProblemService problemService;

    @GetMapping("/problems/{id}")
    public SuccessResponse<ProblemDto> getProblem(@PathVariable String id) {
        ProblemDto problem = problemService.getProblemById(id);
        return new SuccessResponse<>(problem);
    }

    @PostMapping("/submit")
    public Mono<SubmissionResultDto> submitCode(@RequestBody CodeSubmissionDto submission) {
        return compilerService.compileAndJudge(submission);
    }
}