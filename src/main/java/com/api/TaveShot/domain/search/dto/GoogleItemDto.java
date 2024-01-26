package com.api.TaveShot.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class GoogleItemDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "snippet")
    private String snippet;

    private String blog;

    private String createdDate;


    public void modifyBlog(String link) {
        // 정규 표현식
        String regex = "\\.(.*?)\\.";

        // 패턴 컴파일
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(link);

        // 매칭된 부분 찾기
        while (matcher.find()) {
            // . 사이에 있는 단어 출력
            this.blog = matcher.group(1);
        }
    }

}