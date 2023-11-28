package com.api.TaveShot.domain.base;

import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class BaseEntityConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // TODO 지금은 UUID이지만, Security 설정 후 spring Securitycontextholder 에서 값 꺼내기
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
