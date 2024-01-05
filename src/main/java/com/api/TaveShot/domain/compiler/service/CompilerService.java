package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.converter.CompilerConverter;
import com.api.TaveShot.domain.compiler.dto.ProblemInfoDto;
import com.api.TaveShot.domain.compiler.dto.ProblemResponseDto;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CompilerService {

    private final WebClient webClient;

    public CompilerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://solved.ac/api/v3").build();
    }

    public Mono<ProblemInfoDto> getProblemInfo(@NotNull int problemId) {
        return webClient.get()
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
}