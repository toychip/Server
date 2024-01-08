package com.api.TaveShot.domain.compiler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Builder
public class SubmissionResponseDto {
    private int id;
    private boolean executing;
    private String date;
    private Compiler compiler;
    private Result result;

    @Getter
    @Builder
    public static class Compiler {
        private int id;
        private String name;
        private Version version;

        @Getter
        @Builder
        public static class Version {
            private int id;
            private String name;
        }
    }

    @Getter
    @Builder
    public static class Result {
        private Status status;
        private double time;
        private int memory;
        private int signal;
        private String signal_desc;
        private Map<String, Stream> streams;

        @Getter
        @Builder
        public static class Status {
            private int code;
            private String name;
        }

        @Getter
        @Builder
        public static class Stream {
            private int size;
            private String uri;
        }
    }
}
