package com.api.TaveShot.domain.recommend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class UserCrawlingDto {

    @Schema(description = "유저 순위")
    private String rank;

    @Schema(description = "맞은 문제 수")
    private String rightCnt;

    @Schema(description = "틀린 문제 수")
    private String wrongCnt;

    @Schema(description = "유저 레벨")
    private String tier;

    @Schema(description = "맞은 문제 리스트")
    private List<String> solvedProblemList;
}
