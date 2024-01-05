package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.converter.CompilerConverter;
import com.api.TaveShot.domain.compiler.dto.*;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CompilerService {

    private final WebClient solvedwebClient;
    private final WebClient sphereEngineWebClient;
    private final String sphereEngineAccessToken = "fb3a5fa53ca96f45c72c8d0eab9c8207";

    public CompilerService(WebClient.Builder webClientBuilder) {
        this.solvedwebClient = webClientBuilder.baseUrl("https://solved.ac/api/v3").build();
        this.sphereEngineWebClient = webClientBuilder.baseUrl("https://5318779a.compilers.sphere-engine.com/api/v4")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<ProblemInfoDto> getProblemInfo(@NotNull int problemId) {
        return solvedwebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/problem/show")
                        .queryParam("problemId", problemId)
                        .build())
                .retrieve()
                .bodyToMono(ProblemResponseDto.class)
                .map(CompilerConverter::responseToInfoDto)
                .onErrorMap(WebClientException.class, e ->
                        new ApiException(ErrorType._WEB_CLIENT_ERROR))
                .switchIfEmpty(Mono.error(new ApiException(ErrorType._SOLVED_NOT_FOUND)));

    }

    public Mono<SubmissionResultDto> compileAndJudge(CodeSubmissionDto submission) {
        return sphereEngineWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/submissions")
                        .queryParam("access_token", sphereEngineAccessToken)
                        .build())
                .body(BodyInserters.fromValue(createRequestBody(submission)))
                .retrieve()
                .bodyToMono(SubmissionResponseDto.class)
                .map(this::processSubmissionResponse);
    }

    private Map<String, Object> createRequestBody(CodeSubmissionDto submission) {
        Map<String, Object> body = new HashMap<>();
        body.put("source", submission.getSourceCode());
        body.put("compilerId", submission.getCompilerId());

        if (submission.getInput() != null) {
            body.put("input", submission.getInput());
        }
        return body;
    }

    private SubmissionResultDto processSubmissionResponse(SubmissionResponseDto response) {

        boolean isSuccess;
        String message;

        if (response.getResult() != null && response.getResult().getStatus() != null) {
            int statusCode = response.getResult().getStatus().getCode();
            if (statusCode == 15) { // 성공
                isSuccess = true;
                message = "Success";
            } else if (statusCode == 11) { // 컴파일 에러
                isSuccess = false;
                message = "Compilation Error";
            } else if (statusCode == 12) { // 런타임 에러
                isSuccess = false;
                message = "Runtime Error";
            } else { // 기타 채점 결과 상태에 따른 처리
                isSuccess = false;
                message = "Error: Unknown or unhandled error";
            }
        } else {
            // API가 결과를 반환하지 않았을 경우
            isSuccess = false;
            message = "API response is missing the result data";
        }

        return new SubmissionResultDto(isSuccess, message);
        }

}