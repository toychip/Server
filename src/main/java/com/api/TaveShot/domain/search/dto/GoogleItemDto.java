package com.api.TaveShot.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleItemDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "snippet")
    private String snippet;
}
