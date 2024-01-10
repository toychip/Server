package com.api.TaveShot.domain.problem.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemDto {

    @JsonRawValue
    @CsvBindByName(column = "ID")
    private String ID;

    @JsonRawValue
    @CsvBindByName(column = "Title")
    private String Title;

    @JsonRawValue
    @CsvBindByName(column = "Description")
    private String Description;

    @JsonRawValue
    @CsvBindByName(column = "Input Description")
    private String inputDescription;

    @JsonRawValue
    @CsvBindByName(column = "Output Description")
    private String outputDescription;

    @JsonRawValue
    @CsvBindByName(column = "Sample Input")
    private String sampleInput;

    @JsonRawValue
    @CsvBindByName(column = "Sample Output")
    private String sampleOutput;
}
