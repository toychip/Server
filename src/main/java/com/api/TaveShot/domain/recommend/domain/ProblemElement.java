package com.api.TaveShot.domain.recommend.domain;

import com.api.TaveShot.domain.recommend.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Problem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class ProblemElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemId;

    private String title;
    private Integer acceptedUserCount;
    private Integer bojLevel;

    private String bojKey;
    private String bojTagId;
}
