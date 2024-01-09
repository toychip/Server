package com.api.TaveShot.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class GoogleRequestDto {

    @JsonProperty(value = "totalResults")
    private String totalResults;

    @JsonProperty(value = "startIndex")
    private Integer startIndex;
}
