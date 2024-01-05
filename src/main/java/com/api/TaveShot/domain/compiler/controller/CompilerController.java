package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.ProblemInfoDto;
import com.api.TaveShot.domain.compiler.dto.CodeSubmissionDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionResultDto;
import com.api.TaveShot.domain.compiler.service.CompilerService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompilerController {

    private final CompilerService compilerService;
    @GetMapping
    public Mono<SuccessResponse<ProblemInfoDto>> getProblemInfo(@RequestParam int problemId) {
        return compilerService.getProblemInfo(problemId)
                .map(SuccessResponse::new);
    }

    @PostMapping("/submit")
    public Mono<SubmissionResultDto> submitCode(@RequestBody CodeSubmissionDto submission) {
        return compilerService.compileAndJudge(submission);
    }
}