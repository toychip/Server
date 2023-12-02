package com.api.TaveShot.domain.search.service;

import com.api.TaveShot.domain.search.dto.GoogleListResponseDto;
import com.api.TaveShot.domain.search.dto.GoogleResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final String key = "AIzaSyDfz9EptXFpw0Zq4f-VzgGyt3jpQIiyq0s";
    private final String cx = "e1f8e204a35ca41ad";

    public Object findBlog(String query){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com/customsearch/v1")
                .build();

        Mono<GoogleResponseDto> dto = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", key)
                        .queryParam("cx", cx)
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(GoogleResponseDto.class);


        return dto;
    }
}
