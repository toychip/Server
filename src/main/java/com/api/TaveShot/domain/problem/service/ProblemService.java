package com.api.TaveShot.domain.problem.service;

import com.api.TaveShot.domain.problem.dto.ProblemDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class ProblemService {
    private static final Logger log = LoggerFactory.getLogger(ProblemService.class);
    private Map<String , ProblemDto> problemMap;

    @PostConstruct
    public void init() {

        Resource resource = new ClassPathResource("baekjoon_problems.csv");
        try (InputStream is = resource.getInputStream();
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {


            HeaderColumnNameMappingStrategy<ProblemDto> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProblemDto.class);

            CsvToBean<ProblemDto> csvToBean = new CsvToBeanBuilder<ProblemDto>(reader)
                    .withMappingStrategy(strategy)
                    .build();


            List<ProblemDto> problems = csvToBean.parse();


            problemMap = problems.stream()
                    .filter(problem -> problem.getID() != null)
                    .collect(Collectors.toMap(ProblemDto::getID, Function.identity(), (existing, replacement) -> existing));
        } catch (Exception e) {
            log.error("Error loading problems from CSV", e);
        }
    }

    public ProblemDto getProblemById(String id) {
        return problemMap.get(id);
    }
}