package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionRequestDto;
import com.api.TaveShot.domain.compiler.service.CompilerService;
import com.api.TaveShot.domain.compiler.service.ProblemService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompilerController {

    private final ProblemService problemService;
    private final CompilerService compilerService;

    @GetMapping("/problems/{id}")
    public SuccessResponse<ProblemDto> getProblem(@PathVariable String id) {
        ProblemDto problem = problemService.getProblemById(id);
        return new SuccessResponse<>(problem);
    }

    @PostMapping("/submit-code")
    public String submitCode(@RequestBody SubmissionRequestDto submissionRequestDto) {
        String problemId = submissionRequestDto.getProblemId();
        String language = submissionRequestDto.getLanguage();
        String sourceCode = submissionRequestDto.getSourceCode();

        return compilerService.submitCode(problemId, language, sourceCode);
    }
}