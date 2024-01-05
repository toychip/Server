package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CodeSubmissionDto {
    private int problemId;
    private String sourceCode;
    private int compilerId; //Sphere Engine에서 지정한 언어별 컴파일러 ID
    private String input;
}

