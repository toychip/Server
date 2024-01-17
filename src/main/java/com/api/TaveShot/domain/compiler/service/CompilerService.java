package com.api.TaveShot.domain.compiler.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    public String submitCode(String problemId, String language, String sourceCode) {
        RestTemplate restTemplate = new RestTemplate();
        String submitUrl = "http://localhost:5000/submitCode";
        String resultUrl = "http://localhost:5000/result/";

        // 요청 보내고 submission_id 받기
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("problemId", problemId);
        requestMap.put("language", language);
        requestMap.put("sourceCode", sourceCode);

        ResponseEntity<Map> submitResponse = restTemplate.postForEntity(submitUrl, requestMap, Map.class);
        String submissionId = (String) submitResponse.getBody().get("submission_id");

        String result = "결과 처리 중";
        while (result.equals("결과 처리 중")) {
            ResponseEntity<Map> resultResponse = restTemplate.getForEntity(resultUrl + submissionId, Map.class);
            result = (String) resultResponse.getBody().get("result");
            try {
                Thread.sleep(2000);  // 2초 간격으로 확인
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

}
