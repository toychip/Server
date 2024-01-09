package com.api.TaveShot.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class GoogleQueryDto {

    @JsonProperty(value = "request")
    private List<GoogleRequestDto> requests;
}
