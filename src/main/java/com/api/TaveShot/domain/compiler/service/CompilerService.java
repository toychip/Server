package com.api.TaveShot.domain.compiler.service;
import com.api.TaveShot.domain.compiler.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class CompilerService {

    private final WebClient webClient;

    public CompilerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
    }

    public void submitCode(CodeSubmissionDto submissionDto) {
        Mono<String> response = webClient.post()
                .uri("/submit-code")
                .bodyValue(submissionDto)
                .retrieve()
                .bodyToMono(String.class);

        String result = response.block();
        if (result == null || result.contains("error")) {
            throw new RuntimeException("Failed to submit code"); // 이후에 변경
        }
    }

}