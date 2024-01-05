package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.converter.CompilerConverter;
import com.api.TaveShot.domain.compiler.dto.ProblemInfoDto;
import com.api.TaveShot.domain.compiler.dto.ProblemResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CompilerService {

    private final WebClient webClient;

    public CompilerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://solved.ac/api/v3").build();
    }

    public ProblemInfoDto getProblemInfo(int problemId) {
        Mono<ProblemResponseDto> problemResponseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/problem/show")
                        .queryParam("problemId",problemId)
                        .build())
                .retrieve()
                .bodyToMono(ProblemResponseDto.class);

        ProblemResponseDto problemResponse = problemResponseMono.block();
        assert problemResponse != null;
        return CompilerConverter.responseToInfoDto(problemResponse);
    }

}