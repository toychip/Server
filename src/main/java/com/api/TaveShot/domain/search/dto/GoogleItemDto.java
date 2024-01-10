package com.api.TaveShot.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @JsonProperty(value = "pagemap")
    private Pagemap pagemap;

    private String blog;

    private String createdDate;


    public static class Pagemap {

        @JsonProperty(value = "metatags")
        private List<Metatags> metatags;

    }

    public static class Metatags {

        @JsonProperty(value = "article:published_time")
        private String createdTime;


    }

    public void modifyBlog(String link){
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

    public void modifyCreatedDate(){
        String created = pagemap.metatags.get(0).createdTime;
        if(created != null){
            this.createdDate = created.substring(0, 10);
        }
        else this.createdDate = created;
    }
}
