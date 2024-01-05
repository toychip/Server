package com.api.TaveShot.domain.compiler.converter;

import com.api.TaveShot.domain.compiler.dto.ProblemInfoDto;
import com.api.TaveShot.domain.compiler.dto.ProblemResponseDto;

public class CompilerConverter {

    public static ProblemInfoDto responseToInfoDto(final ProblemResponseDto response){
        return new ProblemInfoDto(
                response.getProblemId(),
                response.getTitleKo(),
                response.getLevel(),
                response.getTags()
        );
    }

}