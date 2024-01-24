package com.api.TaveShot.domain.compiler.converter;

import com.api.TaveShot.domain.compiler.domain.BojProblem;
import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class ProblemConverter {

    public static Optional<ProblemDto> convertToDto(BojProblem bojProblem) {
        return Optional.ofNullable(bojProblem)
                .map(bp -> ProblemDto.builder()
                        .id(bp.getId())
                        .title(bp.getTitle())
                        .description(bp.getDescription())
                        .inputDescription(bp.getInputDescription())
                        .outputDescription(bp.getOutputDescription())
                        .sampleInput(bp.getSampleInput())
                        .sampleOutput(bp.getSampleOutput())
                        .build());
    }

    public static Optional<BojProblem> convertToEntity(ProblemDto problemDto) {
        return Optional.ofNullable(problemDto)
                .map(pd -> BojProblem.builder()
                        .id(pd.getId())
                        .title(pd.getTitle())
                        .description(pd.getDescription())
                        .inputDescription(pd.getInputDescription())
                        .outputDescription(pd.getOutputDescription())
                        .sampleInput(pd.getSampleInput())
                        .sampleOutput(pd.getSampleOutput())
                        .build());
    }
}