package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemInfoDto {

    private int problemId;
    private String titleKo;
    private int level;

}
