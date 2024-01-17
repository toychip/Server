package com.api.TaveShot.domain.recommend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class UserCrawlingDto {
    private String rank;
    private String rightCnt;
    private String wrongCnt;
    private String tier;
    private List<String> solvedProblemList;
}
