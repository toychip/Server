package com.api.TaveShot.domain.authorization.dto;

import com.api.TaveShot.domain.Member.domain.Tier;
import lombok.Builder;

@Builder
public record MemberResponse(String gitLoginId, String bojName, Tier tier) {

}
