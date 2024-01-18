package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.dto.SubmissionRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    public String submitCode(SubmissionRequestDto submissionRequestDto) {

        RestTemplate restTemplate = new RestTemplate();
        String submitUrl = "http://localhost:5000/submitCode";
        String resultUrl = "http://localhost:5000/result/";

        ResponseEntity<Map> submitResponse = restTemplate.postForEntity(submitUrl, submissionRequestDto, Map.class);
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
