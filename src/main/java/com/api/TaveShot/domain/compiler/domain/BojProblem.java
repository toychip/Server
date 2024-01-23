package com.api.TaveShot.domain.compiler.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "baekjoon_problems")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class BojProblem {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "InputDescription")
    private String inputDescription;

    @Column(name = "OutputDescription")
    private String outputDescription;

    @Column(name = "SampleInput")
    private String sampleInput;

    @Column(name = "SampleOutput")
    private String sampleOutput;

    @Column(name = "ImagesWithDescriptions")
    private String imagesWithDescriptions;
}

