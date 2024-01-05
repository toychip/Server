package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private String key;
    private int bojTagId;
    private String name;
}