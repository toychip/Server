package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SubmissionResponseDto {
    private int id;
    private boolean executing;
    private Result result;

    @Builder
    @Getter
    public static class Result {
        private Status status; // 채점 상태 객체
        private double time; // 실행 시간
        private int memory; // 메모리 사용량
        private String output; // 프로그램의 출력
        private String stderr; // 표준 에러 출력
        private String cmpinfo; // 컴파일 정보

        @Builder
        @Getter
        public static class Status {
            private int code; // 채점 상태 코드
            private String name; // 채점 상태 이름
        }
    }
}
