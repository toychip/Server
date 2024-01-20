package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionRequestDto;
import com.api.TaveShot.domain.compiler.service.CompilerService;
import com.api.TaveShot.domain.compiler.service.ProblemService;
import com.api.TaveShot.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    public SuccessResponse<String> submitCode(@RequestBody SubmissionRequestDto submissionRequestDto) {
        String result = compilerService.submitCode(submissionRequestDto);
        return new SuccessResponse<>(result);
    }
}