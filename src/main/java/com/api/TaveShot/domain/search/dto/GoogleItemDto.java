package com.api.TaveShot.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleItemDto {

    private String title;
    private String link;
    private String snippet;
}
