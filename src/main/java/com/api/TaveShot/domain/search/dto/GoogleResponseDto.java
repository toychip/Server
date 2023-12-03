package com.api.TaveShot.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleResponseDto {

    @JsonProperty(value = "items")
    private List<GoogleItemDto> items;
}