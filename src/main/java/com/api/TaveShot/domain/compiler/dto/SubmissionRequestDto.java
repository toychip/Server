package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionRequestDto {
    private String problemId;
    private String language;
    private String sourceCode;
}
