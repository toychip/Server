package com.api.TaveShot.domain.Member.domain;

import com.api.TaveShot.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gitId;
    private String gitLoginId;
    private String gitEmail;
    private String gitName;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Tier tier = Tier.BEGINNER;

    public String tierName() {
        return tier.name();
    }
}

