package com.api.TaveShot.domain.recommend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class RecProDetailResponseDto {

    private String title;
    private String problemId;
    private String tier;
    private List<String> bojTag;
}
