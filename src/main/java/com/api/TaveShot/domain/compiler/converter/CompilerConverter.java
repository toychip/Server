package com.api.TaveShot.domain.compiler.converter;

import com.api.TaveShot.domain.compiler.dto.ProblemInfoDto;
import com.api.TaveShot.domain.compiler.dto.ProblemResponseDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionResponseDto;
import com.api.TaveShot.domain.compiler.dto.SubmissionResultDto;

public class CompilerConverter {

    public static ProblemInfoDto responseToInfoDto(final ProblemResponseDto response){
        return new ProblemInfoDto(
                response.getProblemId(),
                response.getTitleKo(),
                response.getLevel(),
                response.getTags()
        );
    }

    public static SubmissionResultDto responseToSubmissionResultDto(final SubmissionResponseDto details) {
        boolean isSuccess = false;
        String message = "Unknown";

        if (details.getResult() != null && details.getResult().getStatus() != null) {
            int statusCode = details.getResult().getStatus().getCode();
            switch (statusCode) {
                case 15: // 성공
                    isSuccess = true;
                    message = "Success";
                    break;
                case 11: // 컴파일 에러
                    message = "Compilation Error";
                    break;
                case 12: // 런타임 에러
                    message = "Runtime Error";
                    break;
                default: // 기타 오류
                    message = "Error: Unknown or unhandled error";
                    break;
            }
        } else {
            message = "API response is missing the result data";
        }

        return new SubmissionResultDto(isSuccess, message);
    }

}