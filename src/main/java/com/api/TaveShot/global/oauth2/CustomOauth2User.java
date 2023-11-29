package com.api.TaveShot.global.oauth2;

import com.api.TaveShot.domain.Member.domain.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Builder
@RequiredArgsConstructor
public class CustomOauth2User implements OAuth2User {

    private final Member member;
    private final Map<String, Object> attributes;

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("GITHUB_MEMBER"));
    }

    @Override
    public String getName() {
        return member.getGitName();
    }
}
