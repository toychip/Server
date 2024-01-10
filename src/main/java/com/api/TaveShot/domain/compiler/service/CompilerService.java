package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.converter.CompilerConverter;
import com.api.TaveShot.domain.compiler.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CompilerService {

    private final WebClient sphereEngineWebClient;

    @Value("${compiler.token}")
    private String sphereEngineAccessToken;

    public CompilerService(WebClient.Builder webClientBuilder) {
        this.sphereEngineWebClient = webClientBuilder.baseUrl("https://5318779a.compilers.sphere-engine.com/api/v4")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }


    // 코드 컴파일 및 채점 결과 반환
    public Mono<SubmissionResultDto> compileAndJudge(CodeSubmissionDto submission) {
        return submitCode(submission)
                .flatMap(this::getSubmissionResult)
                .delayElement(Duration.ofSeconds(10));
    }

    // Sphere Engine API에 코드 제출
    private Mono<Integer> submitCode(CodeSubmissionDto submission) {
        Map<String, Object> requestBody = createRequestBody(submission);

        return sphereEngineWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/submissions")
                        .queryParam("access_token", sphereEngineAccessToken)
                        .build())
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(SubmissionResponseDto.class)
                .map(SubmissionResponseDto::getId);
    }

    // 제출된 코드의 채점 결과 조회
    private Mono<SubmissionResultDto> getSubmissionResult(int submissionId) {
        return sphereEngineWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/submissions/" + submissionId)
                        .queryParam("access_token", sphereEngineAccessToken)
                        .build())
                .retrieve()
                .bodyToMono(SubmissionResponseDto.class)
                .map(CompilerConverter::responseToSubmissionResultDto);
    }

    // Sphere Engine API 요청을 위한 요청 본문 생성
    private Map<String, Object> createRequestBody(CodeSubmissionDto submission) {
        Map<String, Object> body = new HashMap<>();
        body.put("source", submission.getSource());
        body.put("compilerId", submission.getCompilerId());

        return body;
    }

}