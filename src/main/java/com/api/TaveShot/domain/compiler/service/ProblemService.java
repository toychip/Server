package com.api.TaveShot.domain.compiler.service;

import com.api.TaveShot.domain.compiler.dto.ProblemDto;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.input.BOMInputStream;

@Service
public class ProblemService {
    private Map<String , ProblemDto> problemMap;

    @PostConstruct
    public void init() {

        Resource resource = new ClassPathResource("baekjoon_problems.csv");
        try (InputStream inputStream = resource.getInputStream();
             BOMInputStream bomInputStream = new BOMInputStream(inputStream);
             Reader reader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8)) {


            HeaderColumnNameMappingStrategy<ProblemDto> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProblemDto.class);

            CsvToBean<ProblemDto> csvToBean = new CsvToBeanBuilder<ProblemDto>(reader)
                    .withMappingStrategy(strategy)
                    .build();


            List<ProblemDto> problems = csvToBean.parse();


            problemMap = problems.stream()
                    .filter(problem -> problem.getID() != null)
                    .collect(Collectors.toUnmodifiableMap(
                            ProblemDto::getID,
                            Function.identity()
                    ));

        } catch (Exception e) {
            throw new ApiException(ErrorType._WEB_CLIENT_ERROR);
        }
    }
    public ProblemDto getProblemById(String id) {
        ProblemDto problem = problemMap.get(id);

        if (problem == null) {
            throw new ApiException(ErrorType._SOLVED_INVALID_REQUEST); //에러 타입 수정 필요
        }
        return problemMap.get(id);
    }

}