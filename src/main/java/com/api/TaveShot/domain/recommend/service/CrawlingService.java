package com.api.TaveShot.domain.recommend.service;

import com.api.TaveShot.domain.recommend.dto.UserCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CrawlingService {

    private final String baekURL = "https://www.acmicpc.net/user/";

    // 첫번째 크롤링, 사용자의 순위, 랭크, 맞춘 문제 수, 틀린 문제 수, 맞춘 문제 리스트
    public UserCrawlingDto getUserInfo(String userName) throws IOException {
        String rank = null;
        String tier = null;
        String rightCnt = "0";
        String wrongCnt = "0";
        List<String> list = new ArrayList<>();

        String url = baekURL + userName;
        Document document = Jsoup.connect(url).get();

        // 이미지에 따른 티어 찾기
        Element imgTag = document.selectFirst("img.solvedac-tier");
        String number = "";
        if (imgTag != null && imgTag.hasAttr("src")) {
            String src = imgTag.attr("src");
            number = src.replaceAll(".+/(\\d+)\\.svg", "$1");
            log.info("숫자 추출함: " + number);
            tier = number;
        } else {
            tier = "0";
        }

        // statics에서 유저 정보 추출
        String[] info1 = {"등수", "맞았습니다", "틀렸습니다"};
        Element table1 = document.selectFirst("table#statics");
        Elements trs1 = table1.select("tr");
        for(Element tr : trs1){
            Elements thTag = tr.select("th");
            String title = thTag.text();
            if(title.equals(info1[0])){
                Elements tdText = tr.select("td");
                String row_data1 = tdText.text();
                rank = row_data1;
            }
            if(title.equals(info1[1])){
                Elements tdText = tr.select("td");
                String row_data2 = tdText.text();
                rightCnt = row_data2;
            }
            if(title.equals(info1[2])){
                Elements tdText = tr.select("td");
                String row_data3 = tdText.text();
                wrongCnt = row_data3;
            }
        }
        log.info("{}, {}, {}", rank, rightCnt, wrongCnt);

        // 맞은 문제 리스트
        Elements table2 = document.select(".panel.panel-default");
        Element tb2 = table2.get(1);
        Elements trs2 = tb2.select("a");
        for (Element a : trs2){
            String data2 = a.text();
            list.add(data2);
        }


        return UserCrawlingDto.builder()
                        .rank(rank)
                .rightCnt(rightCnt)
                .wrongCnt(wrongCnt)
                .tier(tier)
                .solvedProblemList(list)
        .build();
    }


    // 두번째 기능, 사용자가 속해있는 랭크의 유저들이 푼 문제 추천(?)

}
