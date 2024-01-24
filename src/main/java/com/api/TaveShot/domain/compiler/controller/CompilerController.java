package com.api.TaveShot.domain.compiler.controller;

import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.domain.compiler.service.ProblemService;
import com.api.TaveShot.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompilerController {

    private final ProblemService problemService;
    //private final CompilerService compilerService;

    @Operation(summary = "문제 정보 가져오기", description = "해당 문제 번호의 문제 정보들을 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제 정보 가져오기 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDto.class)))
    })
    @GetMapping("/problems/{id}")
    public ResponseEntity<SuccessResponse<ProblemDto>> getProblem(@PathVariable String id) {
        return problemService.getProblemById(id)
                .map(problemDto -> ResponseEntity.ok(new SuccessResponse<>(problemDto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*@Operation(summary = "코드 컴파일링 및 채점", description = "해당 문제 풀이에 대한 채점 결과를 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채점 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })

    @PostMapping("/submit")
    public SuccessResponse<String> submitCode(@RequestBody @Validated SubmissionRequestDto submissionRequestDto) {
        String result = compilerService.submitCode(submissionRequestDto);
        return new SuccessResponse<>(result);
    }*/
}