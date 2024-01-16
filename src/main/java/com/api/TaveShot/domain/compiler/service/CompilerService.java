package com.api.TaveShot.domain.compiler.service;

import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    private final String pythonServerUrl = "http://localhost:5000"; // 파이썬 서버 주소

    public String submitCode(String problemId, String language, String sourceCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 사용자 입력 데이터를 JSON 형식으로 패키징
        Map<String, String> requestData = new HashMap<>();
        requestData.put("problemId", problemId);
        requestData.put("language", language);
        requestData.put("sourceCode", sourceCode);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestData, headers);

        // 파이썬 서버에 POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                pythonServerUrl + "/submit-code",
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Error occurred while submitting code.";
        }
    }

}
