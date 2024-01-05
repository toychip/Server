package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.ProblemInfoDto;
import com.api.TaveShot.domain.compiler.service.CompilerService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompilerController {

    private final CompilerService compilerService;
    @GetMapping
    public SuccessResponse<ProblemInfoDto> getProblemInfo(@RequestParam int problemId) {
        ProblemInfoDto problemInfo = compilerService.getProblemInfo(problemId);
        return new SuccessResponse<>(problemInfo);
    }
}