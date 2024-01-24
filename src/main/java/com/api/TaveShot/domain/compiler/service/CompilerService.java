/*package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.dto.SubmissionRequestDto;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CompilerService {

    private static final String SUBMIT_CODE_URL = "http://43.202.52.177/submitCode";
    private static final String RESULT_URL = "http://43.202.52.177/result/";
    private static final int MAX_ATTEMPTS = 10;
    private static final long SLEEP_TIME_MILLIS = 5000;

    public String submitCode(SubmissionRequestDto submissionRequestDto) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> submitResponse = restTemplate.postForEntity(SUBMIT_CODE_URL, submissionRequestDto, Map.class);
        String submissionId = (String) submitResponse.getBody().get("submission_id");

        String result = "결과 처리 중";
        int attempts = 0;
        while (result.equals("결과 처리 중") && attempts < MAX_ATTEMPTS) {
            ResponseEntity<Map> resultResponse = restTemplate.getForEntity(RESULT_URL + submissionId, Map.class);
            result = (String) resultResponse.getBody().get("result");
            if (result.contains("정답이 준비되지 않아, 코드를 제출할 수 없습니다.")) {
                throw new ApiException(ErrorType._SUBMIT_PAGE_NOT_FOUND);
            }
            try {
                Thread.sleep(SLEEP_TIME_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            attempts++;
        }

        return result;
    }
}*/
