package com.api.TaveShot.domain.compiler.converter;

import com.api.TaveShot.domain.compiler.domain.BojProblem;
import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import org.springframework.stereotype.Component;

@Component
public class ProblemConverter {

    public ProblemDto convertToDto(BojProblem bojProblem) {
        if (bojProblem == null) {
            return null;
        }

        return ProblemDto.builder()
                .id(bojProblem.getId())
                .title(bojProblem.getTitle())
                .description(bojProblem.getDescription())
                .inputDescription(bojProblem.getInputDescription())
                .outputDescription(bojProblem.getOutputDescription())
                .sampleInput(bojProblem.getSampleInput())
                .sampleOutput(bojProblem.getSampleOutput())
                .imagesWithDescriptions(bojProblem.getImagesWithDescriptions())
                .build();
    }

    public BojProblem convertToEntity(ProblemDto problemDto) {
        if (problemDto == null) {
            return null;
        }

        return BojProblem.builder()
                .id(problemDto.getId())
                .title(problemDto.getTitle())
                .description(problemDto.getDescription())
                .inputDescription(problemDto.getInputDescription())
                .outputDescription(problemDto.getOutputDescription())
                .sampleInput(problemDto.getSampleInput())
                .sampleOutput(problemDto.getSampleOutput())
                .build();
    }
}
