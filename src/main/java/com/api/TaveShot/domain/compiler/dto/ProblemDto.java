package com.api.TaveShot.domain.compiler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemDto {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Input Description")
    private String inputDescription;

    @JsonProperty("Output Description")
    private String outputDescription;

    @JsonProperty("Sample Input")
    private String sampleInput;

    @JsonProperty("Sample Output")
    private String sampleOutput;

    private String problemUrl;

    public String getProblemUrl(){
        this.problemUrl = "https://www.acmicpc.net/problem/" + this.id;
        return this.problemUrl;
    }

}