package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionRequestDto;
import com.api.TaveShot.domain.compiler.service.CompilerService;
import com.api.TaveShot.domain.compiler.service.ProblemService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/submit")
    public String submitCode(@RequestBody Map<String, String> request) {
        return compilerService.submitCode(
                request.get("problemId"),
                request.get("language"),
                request.get("sourceCode")
        );
    }
}