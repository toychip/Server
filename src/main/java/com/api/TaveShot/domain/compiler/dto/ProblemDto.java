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

    @JsonRawValue
    public String getID(){
        return ID;
    }
    @JsonRawValue
    public String getTitle(){
        return Title;
    }

    @JsonRawValue
    public String getDescription(){
        return Description;
    }

    @JsonRawValue
    public String getInputDescription(){
        return inputDescription;
    }

    @JsonRawValue
    public String getOutputDescription(){
        return outputDescription;
    }

    @JsonRawValue
    public String getSampleInput(){
        return sampleInput;
    }

    @JsonRawValue
    public String getSampleOutput(){
        return sampleOutput;
    }

}