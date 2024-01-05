package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemResponseDto {

    private int problemId;
    private String titleKo;
    private int level;
}
