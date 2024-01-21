package com.api.TaveShot.domain.compiler.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionRequestDto {

    @NotEmpty
    private String problemId;

    @NotEmpty
    private String language;

    @NotEmpty
    private String sourceCode;

}
