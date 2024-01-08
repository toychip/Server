package com.api.TaveShot.domain.problem.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemDto {

    @CsvBindByName(column = "ID")
    private String ID;

    @CsvBindByName(column = "Title")
    private String Title;

    @CsvBindByName(column = "Description")
    private String Description;

    @CsvBindByName(column = "Input Description")
    private String inputDescription;

    @CsvBindByName(column = "Output Description")
    private String outputDescription;

    @CsvBindByName(column = "Sample Input")
    private String sampleInput;

    @CsvBindByName(column = "Sample Output")
    private String sampleOutput;

}
