package com.api.TaveShot.domain.recommend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Getter
@ToString
@Builder
public class RecResponseDto {

    private List<RecProDetailResponseDto> result;

    private String rightCnt;
    private String wrongCnt;
    private String rivalCnt;
    private String userRank;
}
